library(RPostgres)
library(ggplot2)
library(dplyr)

#Sybrand Wiersma
# We maken een connectie met de database
con<-dbConnect(RPostgres::Postgres(), dbname="project2", host="localhost", port=5432, user="postgres",password="4616030")

# Uit een query halen (amerika)
data_usa_grouped <- dbGetQuery(con, "Select genre, count(genre)
FROM( SELECT DISTINCT movies.title, genre, country
FROM genres, movies, locations
WHERE genres.tconst = movies.tconst AND genres.tconst = locations.tconst AND country = 'USA' AND movies.type = 'movie') As subquery
GROUP BY genre")
data_usa_percentage <- data_usa_grouped %>% mutate(percentage = count / sum(count) *100)
data_usa_percentage$country <- rep("USA", length(data_usa_percentage$genre))


# Uit een query halen (frankrijk)
data_france_grouped <- dbGetQuery(con, "Select genre, count(genre) FROM(
SELECT DISTINCT movies.title, genre, country
FROM genres, movies, locations
WHERE genres.tconst = movies.tconst AND genres.tconst = locations.tconst AND country = 'France' AND movies.type = 'movie'
) As subquery
GROUP BY genre")
data_france_percentage <- data_france_grouped %>% mutate(percentage = count / sum(count) *100)
data_france_percentage$country <- rep("France", length(data_france_percentage$genre))

data <- rbind(data_france_percentage, data_usa_percentage)

ggplot(data, aes(genre, percentage, fill = country))+ geom_bar(stat = "identity", position="dodge") +  scale_fill_brewer(palette = "Set1")
ggsave("src/main/resources/static/images/genreVis.png", width=15, dpi=700)