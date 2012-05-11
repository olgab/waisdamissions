insert into Participant (user_id, game_id, joinedOn)
select owner_id, game_id, min(creationDate)
from TagEntry
group by owner_id, game_id
order by creationDate asc

