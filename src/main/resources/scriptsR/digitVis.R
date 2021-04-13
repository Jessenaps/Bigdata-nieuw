# Koen Molenaar
library(RPostgres)
con<-dbConnect(RPostgres::Postgres(), dbname="IMDB", host="localhost", port=5432, user="postgres",password="password")
dbListTables(con)

# Eerst laden we de data in een dataframe
data <- dbReadTable(con, "movies")

titles <<- subset(data, select = c(title))

# Dit zijn de functies die zo voor elk nummer en voor iedere regel in titles TRUE of FALSE teruggeeft
table1 <- function(x){ return(grepl("one",tolower(x)) | grepl("1",x)) }

table2 <- function(x){ return(grepl("two",tolower(x)) | grepl("2",x)) }

table3 <- function(x){ return(grepl("three",tolower(x)) | grepl("3",x)) }

table4 <- function(x){ return(grepl("four",tolower(x)) | grepl("4",x)) }

table5 <- function(x){ return(grepl("five",tolower(x)) | grepl("5",x)) }

table6 <- function(x){ return(grepl("six",tolower(x)) | grepl("6",x)) }

table7 <- function(x){ return(grepl("seven",tolower(x)) | grepl("7",x)) }

table8 <- function(x){ return(grepl("eight",tolower(x)) | grepl("8",x)) }

table9 <- function(x) { return(grepl("nine",tolower(x)) | grepl("9",x)) }

# Voor de snelheid gebruiken we zo veel mogelijk concurrency
library(parallel)
cores <- detectCores()
clust <- makeCluster(cores)

# De functies slaan we op in een lijst
list_of_functions <- c(table1, table2, table3, table4, table5, table6, table7, table8, table9)

# Deze functie geeft het aantal regels met het getal erin verschillend per functie
calculate <- function(table_number, titles) { return(table(lapply(titles, table_number))[[2]]) }

# Hier maken we de tabel met per nummer het aantal titles waar het nummer in voorkomt
tables <- parLapply(list_of_functions, calculate, titles=titles, cl=clust)

# Deze stappen zijn nodig om de data kunnen laten zien in een barplot
# 1. numbers wordt een lijst met 1 tot en met 9
# Van de table maken we een dataframe
# Van de kolommen maken we de getallen
# Dan transposen we de dataframe zodat de kolommen de rijen worden en andersom
numbers <- seq(9)
df <- as.data.frame(tables)
names(df)  <- numbers
df <- t(df)
colnames(df) <- "count"

benford <- c(0.301, 0.176, 0.125, 0.097, 0.079, 0.067, 0.058, 0.051, 0.046)

# Uiterlijk barplot
library(RColorBrewer)
coul <- brewer.pal(5, "Set2") 

# We slaan de tabel op in een png bestand
png(filename="src/main/resources/static/images/digitVis.png")
df.bar <- barplot(df[,1], main="Number counts in IMDB titles", xlab = "number", ylab="count", col = coul, ylim = c(0, 40000))
lines(x = df.bar, y = sum(df[,1]) * benford, lwd = 3, col = "black")
points(x = df.bar, y = sum(df[,1]) * benford)
legend("topleft", legend=c("Wet van Benford"), col=c("black"), lty=1:2, cex=0.8)
dev.off()

# De wet van Benford zegt dat de getallen 1-9 in een dataset niet evenvaak voorkomen (11,1%) maar eerder dat de eerste getallen vaker voorkomen dan de getallen erna.
# Hieronder staan de percentages die uit die wet komen.
# In de plot 
# 1 = 33.4%
# 2 = 18.5%
# 3 = 12.4%
# 4 = 7.5%
# 5 = 7.1%
# 6 = 6.5%
# 7 = 5.5%
# 8 = 4.9%
# 9 = 4.2%
# In de grafiek zien we het best met elkaar overeen komt


