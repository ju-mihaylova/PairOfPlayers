package com.sirma.pairofplayers.services;

import com.sirma.pairofplayers.models.Match;
import com.sirma.pairofplayers.models.Record;
import com.sirma.pairofplayers.models.dtos.MatchDuration;
import com.sirma.pairofplayers.models.dtos.PlayerPairResponse;
import com.sirma.pairofplayers.repositories.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PairPlayerCalculator {
    private final MatchRepository matchRepository;

    public PairPlayerCalculator(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<PlayerPairResponse> getPairWithTotalAndMatchDuration(List<Record> records) {
        Map<String, Integer> totalCommonMinutesMap = new LinkedHashMap<>();
        Map<String, List<MatchDuration>> matchDurationMap = new LinkedHashMap<>();

        List<Match> matchesWithPenaltyKick = matchRepository.findMatchesByScoreContainingCharacter("(");

        for (int i = 0; i < records.size(); i++) {
            for (int j = i + 1; j < records.size(); j++) {
                Record record1 = records.get(i);
                Record record2 = records.get(j);

                if (!Objects.equals(record1.getMatch().getId(), record2.getMatch().getId())) {
                    continue;
                }

                boolean isSpecialMatch = matchesWithPenaltyKick.stream()
                        .anyMatch(m -> m.getId().equals(record1.getMatch().getId()));


                int fromMinutes1 = record1.getFromMinutes();
                int toMinutes1 = record1.getToMinutes() == null ? (isSpecialMatch ? 120 : 90) : record1.getToMinutes();
                int fromMinutes2 = record2.getFromMinutes();
                int toMinutes2 = record2.getToMinutes() == null ? (isSpecialMatch ? 120 : 90) : record2.getToMinutes();

                int commonMinutes = getMatchingTime(fromMinutes1, toMinutes1, fromMinutes2, toMinutes2);

                String pairKey = createPairKey(record1.getPlayer().getId(), record2.getPlayer().getId());

                totalCommonMinutesMap.put(pairKey, totalCommonMinutesMap.getOrDefault(pairKey, 0) + commonMinutes);

                if (commonMinutes > 0) {
                    matchDurationMap.computeIfAbsent(pairKey, k -> new ArrayList<>())
                            .add(new MatchDuration(record1.getMatch().getId(), commonMinutes));
                }
            }
        }

        int maxTotalMinutes = totalCommonMinutesMap.values().stream()
                .max(Integer::compare)
                .orElse(0);


        List<PlayerPairResponse> responseList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : totalCommonMinutesMap.entrySet()) {
            if (entry.getValue() == maxTotalMinutes) {
            String pairKey = entry.getKey();
            Integer totalCommonMinutes = entry.getValue();

            String[] players = pairKey.split("_");
            Long playerId1 = Long.parseLong(players[0]);
            Long playerId2 = Long.parseLong(players[1]);

            List<MatchDuration> matchDurations = matchDurationMap.getOrDefault(pairKey, new ArrayList<>());

            PlayerPairResponse pairResponse = new PlayerPairResponse(playerId1, playerId2, totalCommonMinutes, matchDurations);
            responseList.add(pairResponse);
        }
    }

        return responseList.stream()
                .sorted(Comparator.comparing(PlayerPairResponse::getPlayerId1)
                        .thenComparing(PlayerPairResponse::getPlayerId2))
                .collect(Collectors.toList());
    }

    public int getMatchingTime(int fromMinutes1, int toMinutes1, int fromMinutes2, int toMinutes2) {
        int start = Math.max(fromMinutes1, fromMinutes2);
        int end = Math.min(toMinutes1, toMinutes2);
        return (end > start) ? (end - start) : 0;
    }

    public String createPairKey(Long playerId1, Long playerId2) {
        return playerId1 < playerId2 ? playerId1 + "_" + playerId2 : playerId2 + "_" + playerId1;
    }

}
