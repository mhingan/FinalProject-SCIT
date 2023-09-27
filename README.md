# Final Project SCIT 

This is a web application developed using Java and Spring Boot for managing user accounts, creating and viewing posts, and providing statistics. The project is organized into several controllers, each responsible for handling specific functionality.

## Table of Contents
- [Admin Controller](#admin-controller)
- [Post Controller](#post-controller)
- [Statistics Controller](#statistics-controller)
- [User Controller](#user-controller)

### Admin Controller

The `AdminController` manages administrative functions for the application, accessible only by users with the 'ADMIN' authority.

#### Endpoints

- `/admin/panel`: Admin home page.
- `/admin/all-users`: Display all users.
- `/admin/active-users`: Display all active users.
- `/admin/inactive-users`: Display all inactive users.
- `/admin/manage/{id}`: Manage a specific user.
- `/admin/manage/setActive/{id}`: Set a user as active.
- `/admin/manage/setInactive/{id}`: Set a user as inactive.
- `/admin/delete-user/{id}`: Delete a user.
- `/admin/set-user-as-admin/{id}`: Set a user as an admin.
- `/admin/set-admin-as-user/{id}`: Set an admin as a user.

### Post Controller

The `PostController` handles post-related functionality such as creating, editing, and viewing posts.

#### Endpoints

- `/new`: Create a new post.
- `/my-posts`: View posts created by the current user.
- `/edit/{id}`: Edit a specific post.
- `/dashboard/newest`: View posts sorted by the newest.
- `/dashboard/get_that_contains`: Search for posts by title match.
- `/dashboard/category`: Filter posts by category.

### Statistics Controller

The `StatisticsController` provides statistics on user and post data. Accessible only by users with 'ADMIN' authority.

#### Endpoints

- `/admin/statistics`: Display various statistics, including user and post counts.
- `/admin/statistics/getPostsBetween`: Get post count within a specified date range.

### User Controller

The `UserController` manages user-related functionality, including account creation, login, and user profile management.

#### Endpoints

- `/`: Default homepage (can be a login or landing page).
- `/login`: Login page.
- `/dashboard`: User dashboard with post feed.
- `/signup`: User registration page.
- `/my-profile`: View and edit user profile.
- `/{postId}/like`: Like a specific post.

## Dependencies

- Spring Boot
- Spring Security
- Spring Data JPA
- BCrypt (for password hashing)
- Thymeleaf (for templating)

## Getting Started

To run this project locally, follow these steps:

1. Clone the repository to your local machine.
2. Set up a MySQL database and configure the application properties with the database connection details.
3. Build and run the application using your preferred IDE or build tool (e.g., Maven).
4. Access the application through a web browser at the specified port (default is `localhost:8080`).
