/**
 * Description: Custom exceptions
 */

 /**
  * Missing "subject" in received Websocekt message
  * @param {*} message
  */
export function WebsocketSubjectMissing() {
  this.message = "Missing \"subject\" in received Websocekt message";
}

