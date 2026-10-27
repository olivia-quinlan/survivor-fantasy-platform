# Database Setup

The backend validates against an existing schema (`spring.jpa.hibernate.ddl-auto=validate`) and never creates tables, so a new database needs one-time setup before the API will start. The repo does not include real season data. These steps create the tables and load a small made-up season so every page has something to show.

Back to the [README](../README.md).

## Steps

1. **Create an empty PostgreSQL database** and point `DB_URL` at it, or leave `DB_URL` unset to use the default local database. `DB_PASSWORD` must be set either way (see the README's environment variable table).
2. **Create the tables once** by starting the backend with schema generation turned on. Hibernate builds the five tables from the entity classes. Stop the app (Ctrl+C) once it logs `Started BackendApplication`.

   ```bash
   cd survivor-backend
   ./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.jpa.hibernate.ddl-auto=update
   ```

   On Windows PowerShell, quote the argument:

   ```powershell
   mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--spring.jpa.hibernate.ddl-auto=update"
   ```

3. **Load the demo data.** Save the SQL below as `demo-seed.sql` and run it against your database:

   ```bash
   psql -d <your database> -f demo-seed.sql
   ```

4. **Start the backend normally** with `./mvnw spring-boot:run`. It now passes schema validation and serves the demo season at `/api/v1/public/seasons`.

## Demo data

One season with three tribes (two starting tribes and a merged tribe) and four castaways: a winner, a runner-up, a jury member and an early boot, each with a tribe history. The castaways are fictional. Photos, bios and wiki links are left empty.

```sql
INSERT INTO seasons (id, name, premiere_date, finale_date, location, number_of_days, cast_size)
VALUES ('DEMO', 'Demo Season', '2026-01-07 20:00', '2026-02-25 20:00', 'Sample Island', 26, 4);

INSERT INTO tribes (season_id, name, color_hex) VALUES
  ('DEMO', 'Alpha', '#ef4444'),
  ('DEMO', 'Bravo', '#3b82f6'),
  ('DEMO', 'Merged', '#f97316');

INSERT INTO global_contestants (name, hometown) VALUES
  ('Jordan Example', 'Austin, TX'),
  ('Casey Sample', 'Denver, CO'),
  ('Riley Placeholder', 'Portland, OR'),
  ('Morgan Test', 'Miami, FL');

INSERT INTO season_appearances (contestant_id, season_id, final_placement, status, elimination_day, occupation)
SELECT gc.id, 'DEMO', v.place, v.status, v.day, v.job
FROM (VALUES
  ('Jordan Example',    1, 'WINNER',    NULL, 'Teacher'),
  ('Casey Sample',      2, 'RUNNER_UP', NULL, 'Nurse'),
  ('Riley Placeholder', 3, 'JURY',      20,   'Engineer'),
  ('Morgan Test',       4, 'PRE_JURY',  8,    'Chef')
) AS v(name, place, status, day, job)
JOIN global_contestants gc ON gc.name = v.name;

INSERT INTO contestant_tribe_history (appearance_id, tribe_id, phase_order, tribe_status, episode_number, day_number)
SELECT sa.id, t.id, v.phase, v.tribe_status, v.episode, v.day
FROM (VALUES
  ('Jordan Example',    'Alpha',  1, 'INITIAL', 1, 1),
  ('Casey Sample',      'Bravo',  1, 'INITIAL', 1, 1),
  ('Riley Placeholder', 'Alpha',  1, 'INITIAL', 1, 1),
  ('Morgan Test',       'Bravo',  1, 'INITIAL', 1, 1),
  ('Jordan Example',    'Merged', 2, 'MERGE',   6, 12),
  ('Casey Sample',      'Merged', 2, 'MERGE',   6, 12),
  ('Riley Placeholder', 'Merged', 2, 'MERGE',   6, 12)
) AS v(name, tribe, phase, tribe_status, episode, day)
JOIN global_contestants gc ON gc.name = v.name
JOIN season_appearances sa ON sa.contestant_id = gc.id AND sa.season_id = 'DEMO'
JOIN tribes t ON t.name = v.tribe AND t.season_id = 'DEMO';
```

## Status values

`season_appearances.status` drives the status badges in the UI:

| Value | Meaning |
|---|---|
| `WINNER` | Won the season |
| `RUNNER_UP` | Finished second |
| `THIRD_PLACE` | Finished third |
| `JURY` | Voted out after the jury began |
| `PRE_JURY` | Voted out before the jury began |
| `NULL` | Still in the game (season currently airing) |

`contestant_tribe_history.tribe_status` is `INITIAL` (starting tribe), `SWAP`, or `MERGE`.
