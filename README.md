# My Personal Project

## StudyTrack - Course Progress Tracker

**StudyTrack** is a course progress tracking application designed to help students manage their coursework efficiently. The app allows users to create courses, add topics or lectures, and track their confidence levels for each topic. For each topic, users can add any number of lesson objectives and mark which ones they are confident in. Confidence levels for each topic will be automatically calculated based on the percentage of objectives the user has mastered. By providing a clear, consolidated view of their progress, users can quickly identify areas where they need to focus more effort before exams. Future features will include setting milestones, goals, and smart suggestions for study prioritization.

---

### What will the application do?

StudyTrack enables users to:
- Add multiple courses and track their learning progress across various topics or lectures within each course.
- Assign multiple lesson objectives to each topic.
- Record the date of each lecture and update their confidence by marking which objectives they are confident in.
- Automatically calculate confidence as a percentage of mastered objectives for each topic.
- View their overall progress in each course to help assess their readiness for exams.

---

### Who will use it?

The application is designed primarily for students who want to stay organized and on top of their studies. It can be useful for:
- Students studying for exams.
- Individuals managing multiple subjects.
- Self-directed learners looking for a structured way to track their learning progress.

---

### Why this project is of interest to me?

This project is interesting to me because it addresses a common problem students face: managing and organizing their study progress. By creating **StudyTrack**, I aim to:
- Help students prioritize their study time efficiently.
- Track their improvement and ultimately perform better in their exams.

Moreover, it provides me with an opportunity to design a functional application that tackles real-world challenges, allowing me to apply my Java skills in an impactful way.

---

### Features

#### **Course Management**
- Add courses with unique names.
- View a list of all created courses.

#### **Topic Management**
- Add topics to specific courses.
- View all topics associated with a selected course.

#### **Lesson Objective Tracking**
- Define lesson objectives for each topic.
- Mark lesson objectives as mastered.
- Automatically update the confidence level for topics and courses.

#### **Progress Visualization**
- A circular progress bar visualizes the overall course confidence level.
- Confidence is calculated based on the number of mastered lesson objectives.

#### **Data Persistence**
- Save and load course, topic, and lesson objective data to/from a JSON file.

#### **Event Logging**
- Every action (e.g., adding courses, topics, lesson objectives, or marking objectives as mastered) is logged with a timestamp for reference.
---

### User Stories

- As a user, I want to be able to add a new course to my list of courses so that I can begin tracking my progress for that course.
- As a user, I want to be able to add topics or lectures to a specific course with lesson objectives, so that I can break down my studies into manageable units.
- As a user, I want to assign multiple lesson objectives to each topic so that I can specify what I need to learn in each lecture or unit.
- As a user, I want to be able to mark which lesson objectives I am confident in, and have the app calculate my confidence level as a percentage of objectives mastered for each topic.
- As a user, I want to be able to update or modify the details of a course (like its name or objectives) so that I can keep the information current and relevant.
- As a user, I want to be able to remove a course or topic from my list if I no longer need it so that my study tracker stays organized.
- As a user, I want to view all my courses, along with their associated topics, lesson objectives, and progress in one place, so that I can easily assess my learning progress and see what areas I need to focus on.
- As a user, I want to save my course progress, including topics and lesson objectives, so that I can continue my work later without losing any of my tracked progress.
- As a student, I want to load my previously saved course progress so that I can resume where I left off without having to re-enter any information.


### Instructions for End User
## 1. Adding a New Course
- Click the **+ Add Courses** button.
- Enter the course name (e.g., Biology or Math).
- Click **OK** to add the course.

## 2. Adding Topics to a Course
- Select a course from the list.
- Click the **+ Add Topic** button in the course details view.
- Enter the topic name (e.g., Cells or Linear Equations).
- Click **OK** to add the topic.

## 3. Adding Lesson Objectives to a Topic *(Additional Required Action)*
- Select a topic from the course details view.
- Click the **View Lesson Objectives** button.
- In the dialog that appears, click **+ Add Lesson Objective**.
- Enter the lesson objective (e.g., Define the cell theory or Solve equations with one variable).
- Click **OK** to add the lesson objective.

## 4. Marking Lesson Objectives as Mastered *(Additional Required Action)*
- Open the lesson objectives dialog for a topic.
- Check the box next to a lesson objective to mark it as mastered.
- The confidence level of the topic and course will update automatically.

### 5. Viewing Progress *(Visual Component)*
- The circular progress bar in the course details view shows the average confidence level across all topics in the course.

### 6. Saving and Loading Data
- Use the **Save Data** button to save progress to a JSON file.
- Use the **Load Data** button to reload previously saved progress.

---
## Example Logged Events
```plaintext
Tue Nov 26 19:07:09 PST 2024 Added course 'Biology' to the system.
Tue Nov 26 19:07:13 PST 2024 Added topic 'Cells' to course 'Biology'.
Tue Nov 26 19:07:17 PST 2024 Added lesson objective 'Define the cell theory.' to topic 'Cells'.
Tue Nov 26 19:08:41 PST 2024 Marked objective 'Define the cell theory.' as mastered.
Tue Nov 26 19:08:49 PST 2024 Saved data to ./data/studyTrack.json.
```
---
## Phase 4: Task 3
Reflecting on the design in the UML diagram, one major area for improvement is the StudyTrackGUI class. Currently, it handles too many responsibilities, including managing the UI layout, handling user interactions, and triggering updates to the data model. This violates the Single Responsibility Principle and makes the class harder to maintain or extend. If I had more time, I would refactor this class by introducing helper classes to manage specific parts of the GUI, such as a CoursePanelManager for handling course-related UI components and a TopicPanelManager for managing topics and objectives. This would make the code more modular and easier to work with.

In addition, the Course and Topic classes could be further refined. For example, Topic currently calculates its confidence level based on lesson objectives. While this works, the confidence calculation logic could be extracted into a separate utility class or service, making it easier to test and reuse if more advanced progress-tracking logic is needed in the future. Similarly, the Course class could be refactored to include a method that directly returns a summary of progress across its topics, rather than relying on external code to aggregate this information.

These changes would improve the maintainability, readability, and scalability of the program, ensuring that future enhancements or bug fixes can be implemented more efficiently.