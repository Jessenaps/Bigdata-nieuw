library(RPostgres)
library(ggplot2)
library(dplyr)

# We maken een connectie met de database
con<-dbConnect(RPostgres::Postgres(), dbname="project2", host="localhost",
               port=5432, user="postgres",password="4616030")


# De query om alle ratings van Nederlandse films in een dataframe te zetten
data_france_grouped <- dbGetQuery(con, "Select genre, count(genre)
                      From( SELECT DISTINCT title, genre, country
                      FROM genres, movies, locations
                      WHERE genres.tconst = movies.tconst AND
                      genres.tconst = locations.tconst
                      AND country = 'France' AND type = 'movie'
                      ) As subquery
                      Group by genre")


data_france_percentage <- data_france_grouped %>% mutate(percentage = count / sum(count) *100)

ggplot(data_france_percentage, aes(x=genre, y=percentage))+ geom_bar(stat = "identity", fill="steelblue") + coord_flip() + theme_minimal() +geom_text(aes(label = round(percentage, 2)), hjust = -0.2)
ggsave("france_percentages.png")


# De query om alle ratings van Nederlandse films in een dataframe te zetten
data_usa_grouped <- dbGetQuery(con, "Select genre, count(genre)
                                  From( SELECT DISTINCT title, genre, country
                                    FROM genres, movies, locations
                                    WHERE genres.tconst = movies.tconst AND
                                    genres.tconst = locations.tconst
                                    AND country = 'USA' AND type = 'movie'
                                  ) As subquery
                                  Group by genre")


data_usa_percentage <- data_usa_grouped %>% mutate(percentage = count / sum(count) *100)

ggplot(data_usa_percentage, aes(x=genre, y=percentage))+ geom_bar(stat = "identity", fill="steelblue") + coord_flip() + theme_minimal() +geom_text(aes(label = round(percentage, 2)), hjust = -0.2)
ggsave("usa_percentages.png")