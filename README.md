# Pair of Players 
Pair of football players who played together in the same matches for the longest time

## Setup
In the application properties file, use your database username and password.

```
mvn clean compile
mvn clean package
```
## Initialization
Upon the first launch of the application, it's necessary to run it with the run-csv-importer parameter to import the required CSV files into the database. This process may take some time.

To execute the import, use the following command:
```
java -jar target/PairOfPlayers-1.0.0.jar run-csv-importer
```
Once completed, the application will be ready for regular use.

## Algorithm
### Main Goal

 - Calculate the total time two players played together across all matches and determine the pair that played together the longest time in common matches
 - Track how much time they spent together in each individual match
 - Return one or more player pairs (if a single pair is not identified) that have spent the maximum time together, including details for each match

### Complexity

 - The algorithm uses two nested loops to compare each pair of players, resulting in a time complexity of O(n²) where n is the number of player records

### Workflow
1. Initial Setup
    - Two maps are initialized: one that tracks the total common time for each player pair and another that tracks the common time for each pair in specific matches
2. Identify Special Matches
    - The algorithm retrieves a list of matches with penalty kicks, where the total match duration may be 120 minutes instead of the standard 90
3. Iterate through Player Records
    - Using nested loops, the algorithm iterates over every possible pair of records to calculate the time two players spent together on the field during a particular match
4. Calculate Common Minutes
    - The overlapping time the two players spent on the field is calculated. If there is any common time, it's added to the map that accumulates the total time for player pair and the map that track the common time for this specific match
5. Create Player Pair Keys
    - To uniquely identify a pair of players a string in the form of playerId1_playerId2 is generated
6. Identify the Maximum Total Time
    - After all pairs have been processed, the algorithm finds the maximum total common time between any player pair
7. Generate Response
    - A list of PlayerPairResponse objects is built for the pairs that have the maximum common time. Each response object contains information about the player pair IDs, the common minutes they played overall as well as details about the minutes they played together in each common match. The list is returned as a JSON response when the /pairs endpoint is accessed 

### Results
Based on the provided data from the European Football Championship in the four CSV files and the way I grasped the task, a single pair of players who played for the longest time together was not found. In the records file, in the toMinutes column all NULL values were substituted with 90 minutes except for the three matches with penalty kicks, which were taken to be 120 minutes long. Given that, the algorithm described above identified 55 player pairs (formed by 11 English football players) who played together in 7 common matches for the total duration of 660 minutes for each pair found.

## CRUD

The application supports all CRUD operations for players, teams and matches.
### Players
   - Users can:
     - Fetch all players or an individual player by id
     - Add a single player 
     - Edit player details
     - Delete a player

### Teams
- Users can:
   - Fetch all teams or an individual team by id
   - Add a single team
   - Edit team details
   - Delete a team

### Matches
- Users can:
   - Fetch all matches or an individual match by id
   - Add a single match
   - Edit match details
   - Delete a match
