# Project 3 - *Rubit*

**Rubit** is an android app that allows a user to ask other users helping them with a task.

Time spent: **4** weeks spent in total

## Concepts

* **COMMUNITY CONCEPT:** This app aims to build a *COMMUNITY*, not a *SERVICE*

* **Emotional Logo:** A logo which can smile, sad, blink, etc based on the context. Psychological concept to push User
* **Rub Rub Gesture:** Instead of clicking or Swiping, User rub rub (Swipe 2-3 times) an object to see an event. 
  * Psychological concept based on the old story that everyone know: (rub rub the lamp, a genie will appears) -> (rub rub it, someone will help)
  * Develop a unique habit for User, so make them remember the app
  * Marketing concept: "If you need something, then just rub it", similarly to "Uber there" or "Google it"
* **Countdown:** The app will automatically switch to the next events after a specific amount of time. Pyschological concept to push User: "do it more quickly"

## User Stories

The following functionality is completed:

**FIRST_USE**

  * [ ] Splash Screen "Optional"
    * [ ] **Rub Rub gesture** to call the Genie out of the Lamp
    * [ ] **Animation** for the logo
    * [ ] **Small pop up and animation** to help User know that they need to rub rub the lamp
  * [ ] 3-4 **Introduction screen** to summary the functions
  * [ ] **Login & Register** Activity (Refer to the WireFrame)
  * [ ] **FieldSelect** Activity - Ask User choosing their favourite field
  * [ ] **UpdatePortfolio** Activity - Ask User to update their Portfolio
    * [ ] User can update their information"
    * [ ] User can **Upload Pictures and Videos** to their profile

**MAIN_SCREEN_ACTIVITY** 

  * [ ] **Pop-up notification** on the top to show request notification from other users
    * [ ] **Animation** The speech buble popup with "Pudding effect"
    * [ ] **Countdown** The new request will pop up after every 10 seconds
    * [ ] Click the pop-up will head to the **REQUEST_LIST_ACTIVITY**
  * [ ] **Logo** in the middle
    * [ ] **Animation** The logo will show emotion: smile while user typing and sad while user delete
  * [ ] **Go Button** Floating Action Button???
    * [ ] **Rub Rub gesture** instead of clicking the Button
  * [ ] **Request EditText**
    * [ ] **TAG SYSTEM** The app will automatically recognize symbol like # or @ and record the text value after them
      * [ ] # means to people of. Ex: #CoderSchool = to people from CoderSchool
      * [ ] @ has various meaning. May be it mention a specific user, an adress or a time. The app should automatically recognize the type and record it accurately
      * [ ] **Animation** The tagged words with symbol will be changed to bold textStyle and color change to Blue inside the EditText
  * [ ] **Animation** When the keyboard shows up, the Edit Text place above the keyboard, then the Go Button. The logo will become smaller then move to the left. A TextView appears on the Right display exactly what users are typing with White color Tag symbol and Black Text.
  * [ ] **Animation** After User click Go, the Go Button will move to the EditText, grab the text and quickly move to the Logo. The Logo zoom in into a white ball, then rotate, then the **REQUESTING_ACTIVITY** will be called

**REQUESTING_LIST_ACTIVITY** from MainScreenActivity

  * [ ] User can see a list of 5 request bubles from other users
    * [ ] Display in 3 types of buble depends on the length of text (Refer to the WireFrame)
    * [ ] **Mini Badge** sticked to the request bubles to show the type or payment (reward), or the request required something or not, or is there any note, image, etc. attached or not
    * [ ] 2 new random request will popup to replace the old 2 ones after every 10 seconds
  * [ ] **Animation** The speech buble popup with "Pudding effect"
  * [ ] **PullToRequest** User can pull to load a whole 5 new requests

**REQUESTING_ACTIVITY** from MainScreenActivity

  * [ ] Most suitable users's portfolio, whose can handle the task will full-screen appears
    * [ ] User can scan through a list of items in the Porfolio by Vertical scrolling
    * [ ] Users can see more element of an item by Horizontal scrolling (refer to the WireFrame)
    * [ ] Clicking each item in the list, *DetailedPortfolioItem* Activity will show up for more details
  * [ ] **Countdown** User can see the portfolio of each members in 30 seconds, after that, the next profile will show up
  * [ ] User can immediately **Chat** with Candidate or *Save the Profile* for later review
  * [ ] User can **Choose Multiple** Candidates if he/she need more than 1 people

**DETAILED_TASK_ACTIVITY** from MainScreenActivity

  * [ ] User can add **Task name, Requirement and Note** about the task
  * [ ] User can add **Address** or **Pin on Map to select address** for sending request to the address nearby users 
  * [ ] User can *Attach pictures, voice note or video**
  * [ ] User can choose **Payment or different types of Award**

**CHATTING_ACTIVITY** 

  * [ ] User can chat with others in real time
  * [ ] User can review the content of request

**SETTINGS_ACTIVITY**
**OTHERS**  
  
## Wireframe
Here's the illlustration of Rubit App functions:
https://drive.google.com/open?id=0ByLC-o1OX1vCN2lFUXNLNHB3SUU

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
