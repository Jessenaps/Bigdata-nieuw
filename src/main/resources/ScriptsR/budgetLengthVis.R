# Koen Molenaar

library(RPostgres)
library(ggplot2)

# We maken een connectie met de database
con<-dbConnect(RPostgres::Postgres(), dbname="project2", host="localhost", port=5432, user="postgres",password="4616030")
dbListTables(con)

# Uit een query halen we het budget en het aantal minuten
data <- dbGetQuery(con, "SELECT minutes, budget FROM runtimes INNER JOIN business ON runtimes.tconst = business.tconst") 

# We nemen de xlim van 0 tot 300 omdat daarboven alleen uitschieters zijn en de grafiek aleen onduidelijk maken
ggplot(data,aes(x=minutes,y=budget ,color=budget)) +
  geom_point(alpha=0.3) +
  geom_smooth() + labs(y = "Budget $") + xlim(0, 300)

# We slaan het bestand op om in de website te laden
ggsave("budgetLengthVis.png")

