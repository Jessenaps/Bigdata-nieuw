library(RPostgres)
library(ggplot2)

# We maken een connectie met de database
con<-dbConnect(RPostgres::Postgres(), dbname="IMDB", host="localhost", port=5432, user="postgres",password="password")
dbListTables(con)

# Uit een query halen (amerika)
data <- dbGetQuery(con, "Select genre, count(genre)
From(
SELECT DISTINCT title, genre, country
FROM genres, movies, locations
WHERE genres.tconst = movies.tconst AND genres.tconst = locations.tconst AND country = 'USA' AND type = 'movie'
) As subquery
Group by genre")

data_usa_percentage <- data_usa_grouped %>% mutate(percentage = count / sum(count) *100)
ggplot(data_usa_percentage, aes(x=genre, y=percentage))+ geom_bar(stat = "identity", fill="steelblue") + coord_flip() + theme_minimal() +geom_text(aes(label = round(percentage, 2)), hjust = -0.2)

# Uit een query halen (frankrijlk)
data <- dbGetQuery(con, "Select genre, count(genre)
From(
SELECT DISTINCT title, genre, country
FROM genres, movies, locations
WHERE genres.tconst = movies.tconst AND genres.tconst = locations.tconst AND country = 'France' AND type = 'movie'
) As subquery
Group by genre")

data_france_percentage <- data_france_grouped %>% mutate(percentage = count / sum(count) *100)

ggplot(data_france_percentage, aes(x=genre, y=percentage))+ geom_bar(stat = "identity", fill="steelblue") + coord_flip() + theme_minimal() +geom_text(aes(label = round(percentage, 2)), hjust = -0.2)

# We slaan het bestand op om in de website te laden
ggsave("Genre.png")