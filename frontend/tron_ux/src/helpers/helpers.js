/**
 * Description: General helpers
 */

export const DIRECTIONKEYS = Object.freeze([
  "ArrowLeft",
  "ArrowRight",
  "ArrowUp",
  "ArrowDown",
]);

/**
 * Returns the current URL in the format: <scheme:>//<domain>[:<port>]
 */
export function locationURL() {
  const locationPort = window.location.port ? ":" + window.location.port : "";
  return (
    window.location.protocol + "//" + window.location.hostname + locationPort
  );
}
