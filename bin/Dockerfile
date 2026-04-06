FROM openjdk:21-jdk-slim

WORKDIR /app

# Copia as dependências (JARs) da pasta lib
COPY lib/ ./lib/

# Copia os arquivos fonte
COPY src/ ./src/

# Compila todos os .java com as dependências no classpath
RUN javac -cp "lib/*" -d . src/*.java

# Roda o Worker com as dependências
CMD ["sh", "-c", "java -cp '.:lib/*' Worker"]
