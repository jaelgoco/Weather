# Weather Challenge App 🌦️☁️🌡️

Version: 1.0

Language: Java

## Introduction

Welcome to the Weather Challenge App! This application serves as a weather information 
service that exposes three endpoints for users to access weather data. It leverages 
a separate weather API to provide accurate and up-to-date weather information. 
Please note that this app is for educational and learning purposes only.

## Endpoints

1. **POST /api/v1/weather-forecast/{city}** - Create Weather Forecast
- Use this endpoint to fetch the weather forecast for a specific city for the upcoming days from the weather API. The number of days for the forecast is configured in the application properties. The fetched data will be stored in the database for future reference.

2. **GET /api/v1/weather-forecast/{city}** - Get Weather Forecast
- Retrieve the weather forecast for a specific city from the database. The number of days for the forecast is configured in the application properties.

3. **GET /api/v1/weather-forecast/forecast** - Get All Weather Forecast
- Fetch weather forecasts for all cities from the database. The number of days for the forecast is configured in the application properties.

## Installation ⚙️
1. Fork/Clone/Download this repo

   `git clone https://github.com/jaelgoco/Weather.git`

2. Navigate to the directory

   `cd weatherchallenge`

3. Run Gradle clean build using the gradle wrapper

   `./gradlew clean build`

## Prerequisites

Before you can use `Docker-compose` or simply run the application, please ensure you do have the following prerequisites met.

1. **Docker** installed - [link](https://docs.docker.com/get-docker/)
2. **Key** generated - Generate/retrieve your key by signing up at [Weather API](https://www.weatherapi.com/) and copying your key from [here](https://www.weatherapi.com/my/)
3. **Credentials** configured - This can be done manually by going to ./resources/application.properties and adding your Weather API key to the **weather.api.webclient.key** property

**Important**: Your requests will fail if you do not do step #2 and #3 and configure your API key

## Quick Start 

This section will explain how you can quickly set up this application without Docker

Although this app is relatively simple to set up, if you have Docker already installed, it is recommended to follow the Docker Quick Start below. If you choose to set it up yourself, these are the steps:

1. **PostgreSQL** installed - Install PostgreSQL any way you wish
2. **Properties** configured - Uncomment and configure the following properties in ./weatherchallenge/application.properties
   ```
   #spring.datasource.url=
   #spring.datasource.username=
   #spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=create (leave this one as is)
   ```
3. Run **./src/main/java/com/jason/weatherchallenge/WeatherChallengeApplication.java** using your IDE of choice

## Docker Quick Start 🐳

This section will explain how you can quickly use this image with `Docker-compose`.

### Using `docker-compose`

You can use the `docker-compose.yml` file this single command:

```bash
docker-compose up
```

This command will create the two containers needed to run this app without any further installation.
1. **app (localhost:8082)** - Container running the Weather Challenge app
2. **db (localhost:5432)** - Container running the latest version of postgresql

**Important**: Make sure to stop any processes running on these two ports before running your docker compose

