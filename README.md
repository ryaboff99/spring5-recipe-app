[![CircleCI](https://circleci.com/gh/springframeworkguru/spring5-recipe-app.svg?style=svg)](https://circleci.com/gh/springframeworkguru/spring5-recipe-app)

# Spring Boot Recipe Application

This app is an emulation of a recipe site. It displays recipes with their values on the main (index) page using Hibernate and Thymeleaf. It also contains the h2 database. This application uses the following relationships:

@OneToOne - in Ingredients, Notes, Recipes

@OneToMany - in the Recipe

@ManyToOne - in Ingredients

@ManyToMany - in Category, Recipe



Images for uploading are located at resources/static/images

This repository is for an example application built in my Spring Framework 5 - Beginner to Guru (https://go.springframework.guru/spring-framework-5-online-course)
