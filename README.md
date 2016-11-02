# Project 4 - *Rubit*

**Rubit** is an android app that allows a user to ask other users helping them with a task.

Time spent: **4** weeks spent in total

## Contributors

| Email                     | Name              | Role      |
| --------------------------|:-----------------:| ---------:|
| rubit1359@gmail.com       | :Ethan Tran       | Developer |
| manetivinay@gmail.com     | :Vinay Maneti     | Developer |
| tien.workinfo@gmail.com   | :Tien Nguyen      | Developer |
## Concepts

* **COMMUNITY CONCEPT:** This app aims to build a *COMMUNITY*, not a *SERVICE*

* **Emotional Logo:** A logo which can smile, sad, blink, etc based on the context. Psychological concept to push User
* **Rub Rub Gesture:** Instead of clicking or Swiping, User rub rub (Swipe 2-3 times) an object to see an event. 
  * Psychological concept based on the old story that everyone know: (rub rub the lamp, a genie will appears) -> (rub rub it, someone will help)
  * Develop a unique habit for User, so make them remember the app
  * Marketing concept: "If you need something, then just rub it", similarly to "Uber there" or "Google it"
* **Countdown:** The app will automatically switch to the next events after a specific amount of time. Psychological concept to push User: "do it more quickly"

## User Stories

The following functionality is completed:

**FIRST_USE**

  * [ ] Opening Screen **_(Optional)_**
    * [x] ~~Splash screen as the moment~~
    * [ ] **Rub Rub gesture** to call the Genie out of the Lamp
    * [ ] **Small pop up and animation** to help User know that they need to rub rub the lamp
    * [ ] **_(Optional)_** **Animation** for the logo
  * [ ] **_(Optional)_** 3-4 **Introduction screen** to summary the functions
    * [ ] 3 - 4 activities
    * [ ] Replace by fragment
    * [ ] **_(Optional)_** Slide animation or something else
  * [ ] **Login** Activity
    * [ ] Login through current app
    * [ ] Third parties login (Facebook, Google, etc)
  * [ ] **Register** Activity
    * [ ] Register through current app
    * [ ] Third parties register (Facebook, Google, etc)
  * [ ] **FieldSelect** Activity - Ask User choosing their favourite field
    * [ ] Static selected field at the moment
    * [ ] **_(Optional)_** Animation moving around randomly applied.
  * [ ] **UpdatePortfolio** Activity - Ask User to update their Portfolio
    * [ ] User can update their information
    * [ ] **_(Optional)_** User can **Upload Picture/GIF** as an avatar
    * [ ] **_(Optional)_** User can **Upload Video** as an avatar

**MAIN_SCREEN_ACTIVITY** 

  * [ ] **Pop-up notification** on the top to show request notification from other users
    * [ ] Click the pop-up will head to the **REQUEST_LIST_ACTIVITY**
    * [ ] **_(Optional)_** **Animation** The speech bubble popup with "Pudding effect"
    * [ ] **_(Optional)_** **Countdown** The new request will pop up after every 10 seconds
  * [ ] **Logo** in the middle
    * [ ] **_(Optional)_** **Animation** The logo will show emotion: smile while user typing 
    * [ ] **_(Optional)_** **Animation** The logo will show emotion: sad while user delete
  * [ ] **Go Selection**
    * [ ] **Rub Rub gesture** Clicking the Button as Floating Action Button
    * [ ] The **REQUESTING_ACTIVITY** will be called if it is selected
    * [ ] **_(Optional)_** **Rub Rub gesture**
  * [ ] **Request EditText**
    * [ ] **_(Optional)_** **TAG SYSTEM** The app will automatically recognize symbol like # or @ and record the text value after them
      * [ ] # means to people of. Ex: #CoderSchool = to people from CoderSchool
      * [ ] @ has various meaning. May be it mention a specific user, an address or a time. The app should automatically recognize the type and record it accurately
      * [ ] **Animation** The tagged words with symbol will be changed to bold textStyle and color change to Blue inside the EditText
  * [ ] **_(Optional)_** **Extra Animation** 
    * [ ] When the keyboard shows up, the Edit Text place above the keyboard, then the Go Button.
    * [ ] The logo will become smaller then move to the left.
    * [ ] Apply White color for Tag symbol #.
    * [ ] Apply ? (blue) color for Tag symbol @.
    * [ ] Special effect on Go Selection
        * [ ] The Go Button will move to the EditText
        * [ ] Grab the text and quickly move to the Logo
        * [ ] The Logo zoom in into a white ball, then rotate then the **REQUESTING_ACTIVITY** will be called

**REQUESTING_LIST_ACTIVITY** from MainScreenActivity

  * [ ] User can see a list of 5 request from other users
    * [ ] Display list with 5 elements as minimum
    * [ ] **_(Optional)_** 2 new random request will popup to replace the old 2 ones after every 10 seconds
    * [ ] **_(Optional)_** Display in 3 types of bubble depends on the length of text (Refer to the WireFrame)
    * [ ] **_(Optional)_** **Mini Badge** sticks to the request to show the type or payment (reward), or the request required something or not, or is there any note, image, etc. attached or not
    * [ ] **_(Optional)_** **Animation** The speech bubble popup with "Pudding effect"
  * [ ] **Pull To Request** User can pull to load a whole 5 new requests
    * [ ] Refresh list
    * [ ] Color progress bar appear to notify + Make the blur/transparent background
    * [ ] **_(Optional)_** Animation for that background.
  * [ ] **Loading more effect** if user scroll down
    * [ ] Add 5 more to list
    * [ ] Color progress bar appear to notify
    * [ ] **_(Optional)_** Animation for that Color progress bar.

**REQUESTING_ACTIVITY** from MainScreenActivity

  * [ ] Most suitable users's portfolio, whose can handle the task will full-screen appears
    * [ ] A list of items in the Portfolio by Vertical scrolling
    * [ ] More element of an item by Horizontal scrolling (refer to the WireFrame)
    * [ ] **_(Optional)_** Clicking each item in the list, **DetailedPortfolioItem** Activity will show up for more details
  * [ ] **Countdown** User can see the portfolio of each members in 30 seconds, after that, the next profile will show up
  * [ ]  With the selected candidate
    * [ ] User can immediately **Chat** if that both are online
    * [ ] **_(Optional)_** Follow/Subscribe: **Save the Profile** for later review
    * [ ] **_(Optional)_** User can **Choose Multiple** Candidates if he/she need more than 1 people

**DETAILED_TASK_ACTIVITY** from MainScreenActivity

  * [ ] User can add **Task name, Requirement and Note** about the task
  * [ ] User can add **Address** 
  * [ ] **_(Optional)_** **Pin on Map to select address** for sending request to the address nearby users 
  * [ ] **_(Optional)_** User can **Attach pictures, voice note or video**
  * [ ] **_(Optional)_** User can choose **Payment or different types of Award**

**CHATTING_ACTIVITY** 

  * [ ] User can chat with others in real time
  * [ ] User can review the content of request

**SETTINGS_ACTIVITY (Optional)**

**OTHERS** 
* [ ] **_(Bonus)_** **Credit System:** When Users help others, they will receive credit
* [ ] **_(Bonus)_** **Friend System:** Just social network functions like Facebook
* [ ] **_(Bonus)_** **Mention Function:** If users can't handle the job, but they know who can do it, they can just pick him (for one who already has Rubit account) or send a message with a link (for someone who does not have account in Rubit - This also play roles as an invitation to join). The Introducer will also get a part of Credit.
* [ ] **_(Bonus)_** **Badge System:** Users can receive badge from authorized organizations to prove their achievements. Ex: CoderSchool badge == graduates from CC
* [ ] **_(Bonus)_** **Payment System:** Considering ~
  
## Wireframe
Here's the illustration of Rubit App functions: [RubitWireframe] (https://drive.google.com/open?id=0ByLC-o1OX1vCN2lFUXNLNHB3SUU)

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.


## License

    Copyright [2016] [Rubit.vn]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
