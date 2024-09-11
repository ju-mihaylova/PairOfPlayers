package com.sirma.pairofplayers.services;

import com.sirma.pairofplayers.exceptions.ResourceNotFoundException;
import com.sirma.pairofplayers.helpers.Constants;
import com.sirma.pairofplayers.helpers.CsvReader;
import com.sirma.pairofplayers.models.Match;
import com.sirma.pairofplayers.models.Player;
import com.sirma.pairofplayers.models.Record;
import com.sirma.pairofplayers.models.dtos.PlayerPairResponse;
import com.sirma.pairofplayers.repositories.MatchRepository;
import com.sirma.pairofplayers.repositories.PlayerRepository;
import com.sirma.pairofplayers.repositories.RecordRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RecordService extends AbstractService<Record> {
    private final RecordRepository recordRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final PairPlayerCalculator pairPlayerCalculator;

    public RecordService(
            RecordRepository recordRepository,
            PlayerRepository playerRepository,
            MatchRepository matchRepository,
            PairPlayerCalculator pairPlayerCalculator
    ) {
        this.recordRepository = recordRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.pairPlayerCalculator = pairPlayerCalculator;
    }

    private static final String filepath = Constants.RECORD_FILE_PATH;

    public void saveRecordsFromCsvToDB() throws IOException {
        CsvReader csvReader = new CsvReader(filepath);
        List<String[]> rows = csvReader.readAll();
        csvReader.close();
        for (String[] row : rows) {
            Record record = new Record();
            List<Record> allRecords = recordRepository.findAll();
            long id = Long.parseLong(row[0]);
            if (doesIdExist(allRecords, id)) {
                continue;
            }
            Long playerId = Long.parseLong(row[1]);
            Long matchId = Long.parseLong(row[2]);
            record.setId(id);
            Player playerFromDb = playerRepository.findById(playerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Player", "playerId", playerId));
            record.setPlayer(playerFromDb);
            Match matchFromDb = matchRepository.findById(matchId)
                    .orElseThrow(() -> new ResourceNotFoundException("Match", "matchId", matchId));
            record.setMatch(matchFromDb);
            record.setFromMinutes(Integer.parseInt(row[3]));
            var toMinutes = "null".equalsIgnoreCase(row[4]) ? null : Integer.parseInt(row[4]);
            record.setToMinutes(toMinutes);
            recordRepository.save(record);
        }
    }

    public List<PlayerPairResponse> getPairPlayers() {
        List<Record> records = recordRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return pairPlayerCalculator.getPairWithTotalAndMatchDuration(records);
    }
}
