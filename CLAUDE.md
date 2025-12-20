# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Xiuyuan Admin System is a Spring Boot-based enterprise admin management system with MongoDB and Redis. The system
provides user, department, and position management modules with RESTful APIs.

## Architecture

Multi-module Maven project with two main modules:

- `xiuyuan-common`: Shared utilities, constants, and base classes
- `xiuyuan-admin`: Main application with controllers, services, and business logic

## Key Technologies

- Spring Boot 3.5.9 with Java 21
- MongoDB for data persistence
- Redis for caching (database 9)
- SA-Token for authentication
- MapStruct for object mapping
- Lombok for code generation
- Hutool utility library

## Development Commands

```bash
# Build entire project
mvn clean install

# Build without tests
make install

# Run the application
mvn spring-boot:run -pl xiuyuan-admin

# Package application
mvn package -pl xiuyuan-admin
```

## Configuration

Main configuration file: `xiuyuan-admin/src/main/resources/application.yml`

Key settings:

- Server port: 8080
- Context path: /api
- MongoDB URI configured with authentication
- Redis connection to 100.91.153.120:6379 (database 9)
- SA-Token with Bearer prefix and 30-day timeout

## Code Organization

Controllers in `xin.xiuyuan.admin.controller` follow RESTful conventions:

- User management: `SysUserController`
- Department management: `SysDeptController`
- Position management: `SysPostController`
- Role management: `SysRoleController`
- Authentication: `SysLoginController`

Business logic in service layer uses repository pattern with MongoDB repositories.

## Authentication

Uses SA-Token framework with JWT-style tokens. Login returns Bearer token to be used in Authorization header. Concurrent
login is enabled - same account can login from multiple devices.

## Database

All MongoDB entities extend base classes with common fields. Repositories use Spring Data MongoDB with custom query
methods where needed. Redis is used for caching with connection pooling configured.