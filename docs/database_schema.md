## user

- id: primary key (auto number)
- username: login email. Unique, not empty.
- password: bcrypt hash. Not empty.
- enabled: true or false. If false, the user can't log in.
- account_expired: true or false
- account_locked: true or false
- password_expired: true or false

## role

- id: primary key
- authority: ROLE_ADMIN or ROLE_USER. Unique.

## user_role

- user_id: foreign key to user.id
- role_id: foreign key to role.id
- Primary key: user_id + role_id together (a user can't get the same role twice)

## task

- id: primary key
- title: not empty, up to 150 characters
- description: optional, longer text
- deadline: date, required
- status: TODO, IN_PROGRESS or DONE. Default TODO.
- assigned_to_id: foreign key to user.id. Required.
- date_created: filled in automatically when the task is created
- last_updated: filled in automatically when the task is changed

## Relationships

- One user has many tasks (user.id to task.assigned_to_id).
- Each task belongs to exactly one user.
- Users and roles are many-to-many through user_role.