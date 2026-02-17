# BingBong User Guide

![BingBong UI screenshot](./Ui.png)

## Introduction
BingBong is a trusty task manager that allows you to handle your to-do list with ease. It has the following features:
- Add tasks
- Mark tasks as done
- Mark tasks as incomplete
- Delete tasks
- List tasks
- Provide reminders on impending tasks
- Search for tasks by substring

BingBong supports **3 kinds of tasks**:
- Todos (tasks with no associated dates)
- Deadlines (tasks with a due date)
- Events (tasks that have a start date and an end date)

## Adding Todos: `todo`

Adds a todo to the task list.

**Format**: `todo TASK_NAME`

**Example**: `todo Do the dishes`

**Expected Output**:
```
You're getting busier. I've added this task:
[T][ ] Do the dishes
Now you have 1 task(s) in the list.
```

## Adding Deadlines: `deadline`

Adds a deadline to the task list.

**Format**: `deadline TASK_NAME /by DUE_DATE`

**Example**: `deadline Finish homework /by 17/2/2026 22:00`

**Expected Output**:
```
You're getting busier. I've added this task:
[D][ ] Finish homework (by: 17 Feb 2026, 10:00 pm)
Now you have 2 task(s) in the list.
```

## Adding Events: `event`

Adds an event to the task list.

**Format**: `event TASK_NAME /from START_DATE /to END_DATE`

**Example**: `event Chess training /from 18/2/2026 10:00 /to 18/2/2026 12:00`

**Expected Output**:
```
You're getting busier. I've added this task:
[E][ ] Chess training (from: 18 Feb 2026, 10:00 am to: 18 Feb 2026, 12:00 pm)
Now you have 3 task(s) in the list.
```

## Marking Tasks as Complete: `mark`

Marks the task corresponding to the selected index as complete.

**Format**: `mark TASK_INDEX`

**Example**: `mark 1`

**Expected Output**:
```
Congratulations on being hardworking for once! This task has been marked as done:
[T][X] Do the dishes
```

## Unmarking Tasks: `unmark`

Unmarks the task corresponding to the selected index, thereby making the task incomplete.

**Format**: `unmark TASK_INDEX`

**Example**: `unmark 1`

**Expected Output**:
```
This task has been marked as incomplete:
[T][ ] Do the dishes
```

## Deleting Tasks: `delete`

Deletes the task corresponding to the selected index, from the task list.

**Format**: `delete TASK_INDEX`

**Example**: `delete 1`

**Expected Output**:
```
Alright, I've gotten rid of this task:
[T][ ] Do the dishes
Now you have 2 task(s) in the list.
```

## Listing Tasks: `list`

Displays a list of all the tasks that are currently being tracked.

**Format**: `list`

**Expected Output**:
```
Here are the tasks that you have added to the list:

[D][ ] Finish homework (by: 17 Feb 2026, 10:00 pm)
[E][ ] Chess training (from: 18 Feb 2026, 10:00 am to: 18 Feb 2026, 12:00 pm)
```

## Receiving Reminders: `remind`

Displays a list of the outstanding tasks that will be due soon, within the selected number of days from the current date.

**Note**:
- Todos will never be due.
- For a deadline, its associated date is taken to be its "due date".
- For an event, its start date is taken to be its "due date".

**Format**: `remind NUM_DAYS_FROM_CURRENT_DATE`

**Example**: `remind 5`

**Expected Output**:
```
Don't forget! Here are all the outstanding tasks that you should take note of, over the next 5 day(s):

[D][ ] Finish homework (by: 17 Feb 2026, 10:00 pm)
[E][ ] Chess training (from: 18 Feb 2026, 10:00 am to: 18 Feb 2026, 12:00 pm)
```

## Exiting the Chatbot: `bye`

Quits the chatbot.

**Format**: `bye`

**Expected Output**:
```
Hasta la vista, baby!
```

## Additional Notes
- For the command formats provided above, note that words in `UPPER_CASE` correspond to the parameters which you are to supply.
- The command formats should be followed strictly.
  - For example, if additional parameters are passed alongside the `list` command (eg. `list 3`), an error will occur.
- Command names are case-insensitive.
- All task indices follow 1-indexing.
- Please input dates using the correct format of `d/M/yyyy HH:mm`. For example, to input the date `2 Jan 2003, 1:18 pm`, enter `2/1/2003 13:18`.
- Your recorded tasks will be saved in `./data/tasks.txt`.
  - If `tasks.txt` does not exist in the data path, or if `tasks.txt` is incorrectly formatted, an empty task list will be initialised.
