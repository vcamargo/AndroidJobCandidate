![Android CI](https://github.com/vcamargo/AndroidJobCandidate/workflows/Android%20CI/badge.svg)

# Android Job Candidate

### Task Description

Fix all of the TODOs and rewrite the project so it reflects your coding style and preferred way of displaying a list of items and a details page.
We expect that the assignment will be written in Kotlin

When clicking one of the items in the list, the details of that item should be shown.
When loading data from the Api, there should be a ProgressBar visible.
In the case of a connection timeout, there should be a fullscreen error message with a retry button.
Clicking the retry button should make a new request to the api.

Your solution should be something you would put into production.
This means that we expect that the app is stable and performs well in all possible use cases

*At the interview we expect you to walk us through the code and explain what you have done.*

# Solution

### Main App Components

The app was developed using the following "components" :

[Navigation Component](https://developer.android.com/guide/navigation)

[Databinding](https://developer.android.com/topic/libraries/data-binding)

[LiveData](https://developer.android.com/topic/libraries/architecture/livedata)

[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

[RXJava](https://github.com/ReactiveX/RxJava)

[Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

### Build Variants

The app has two main build variants, debug and mock.

Debug will use the live API data, reaching the backend.

Mock will use mocked data from MockRepository class.
If you select the first item of the list it will show a network error screen (this is the intended behavior used by the UI tests).

### UI Tests

UI Tests are taking advantage of the class MockRepository.kt where we have total control over the
responses that we'll send back to the UI.

That's why the UI tests are currently being executed using the mock build variant.

If you want to run the UI Tests using the live API data, please comment out app/build.gradle
line 25 "testBuildType "mock". But doing so it will lead to UI test failures due to network call latency.