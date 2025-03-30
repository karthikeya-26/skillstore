# Skill Store

## Overview
Skill Store is a web application built using JAX-RS that connects users looking for specific skill sets with skilled individuals. Users can list their skills for others to find and can book and collaborate with those offering the skills they need.

## Features
- Users can create, read, update, and delete their profiles.
- Users can add, update, and remove skills to/from their profile.
- Authentication filter to secure endpoints (future improvements can include caching and advanced auth mechanisms).
- RESTful API built with JAX-RS.
- Database management using custom query builders.

## Project Structure


## API Endpoints (Resources folder)

### /users
- **GET**: Retrieve a list of users
- **POST**: Create a new user
- **PUT**: Update user details
- **DELETE**: Delete a user

### /users/{userName}/skills
- **GET**: Retrieve the skills of a specific user
- **POST**: Add new skills to a user's profile
- **PUT**: Update the skills of a user
- **DELETE**: Remove skills from a user's profile

## Services Folder
- **UserDetailsService**: contains /user logic to process data and validate and store it.
- **SkillsService**: contains /user/{user}/skills logic to process and validate and store it.

## Exceptions Folder
- Custom Exceptions to send proper Error Messages to send as Response

## Models Folder
- Contains classes that map to database tables.

## Query Builder 
- contains
-  **mysql** : contains mysql query builders
-  **psql** : contains postgres sql query builders.

## Query Layer
-contains classes such as **Select**, **Insert**, **Update**, **Delete** and other classes which
are used to queries database abstracted queries.

## Utils 
- contains
- ApplicationPropertiesHelper to get application properties.
- QueryLayerSetup to create database tables from json file and create enum files for writing queries.
