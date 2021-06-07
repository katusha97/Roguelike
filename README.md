# Rouglulike

Запустить сервер можно так:
```
./gradlew run --args="server"
```
По дефолту сервер запускается на localhost на порте 8000. Но можно указать и другой хост/порт:
```
./gradlew run --args="server --host HOST --port PORT"
```

Запуск клиента аналогичен (по дефолту localhost:8000):
```
./gradlew run --args="client"
```
С указанием хоста/порта
```
./gradlew run --args="client --host HOST --port PORT"
```
![img.png](img.png)
Описание [тут](https://docs.google.com/document/d/1zrM2FN4-_08l6VBR3n_ot-vcXs4cVrT5m_9jCdSZ5IA/edit?usp=sharing).