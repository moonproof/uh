<h3>Необходимое окружение</h3>
1. jdk-11
2. docker
3. maven

<h3>Как запустить проект</h2>
1. Готовим артефакт в директория docker: `mvn clean package -DoutputDirectory='docker'`
2. Переходим в директорию docker `cd docker`
3. И выполняем следующую команду: `docker-compose-up`
Проект поднят, состоит из spring-boot-application и postgres

<h3>Эндпоинты</h3>
Документация по урлу `/swagger`

тестовый проект для подключения миграции данных, свагера и разворачивание в докер контейнере