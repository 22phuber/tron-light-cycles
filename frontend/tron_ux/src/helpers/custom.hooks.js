import { useEffect, useRef } from "react";
/**
 * React Polling Hook
 * Source: https://stackoverflow.com/questions/46140764/polling-api-every-x-seconds-with-react/60498111#60498111
 * https://overreacted.io/making-setinterval-declarative-with-react-hooks/
 * @param {*} callback
 * @param {*} delay
 */
export const useInterval = (callback, delay) => {
  const savedCallback = useRef();
  useEffect(() => {
    savedCallback.current = callback;
  }, [callback]);
  useEffect(() => {
    function tick() {
      savedCallback.current();
    }
    if (delay !== null) {
      const id = setInterval(tick, delay);
      return () => clearInterval(id);
    }
  }, [delay]);
};
