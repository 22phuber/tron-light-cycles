import React from "react";
import { convertRGB, getRandomName } from "./helpers";

describe("convertRGB", () => {
  // Reference objects
  const refRGBObject = { r: "0", g: "0", b: "0", a: "1" };
  const refRGBString = "rgb(0,0,0)";

  const rgbObject = convertRGB(refRGBString);
  const rgbString = convertRGB(refRGBObject);

  test('convertRGB using String "' + refRGBString + '"', () => {
    expect(rgbObject).toMatchObject(refRGBObject);
  });

  test('convertRGB using Object "' + JSON.stringify(refRGBObject) + '"', () => {
    expect(rgbString).toMatch(refRGBString);
  });
});
