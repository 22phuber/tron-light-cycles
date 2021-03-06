/**
 * Description: General helpers
 */

// Supported direction keys
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

/**
 * Converts and returns, depending on input param:
 * - String "rgb(0,0,0)" to {"r":0,"g":0,"b":0,"a":1}
 * - Object {"r":0,"g":0,"b":0,"a":1} to "rgb(0,0,0)"
 * Alpha is ignored
 * @param {*} __color String in format "rgb(<0-255>,<0-255>,<0-255>)" or Object in format {"r":<0-255>,"g":<0-255>,"b":<0-255>,"a":1}
 */
export function convertRGB(__color) {
  if (/rgb/i.test(__color)) {
    var rgb = __color.replace(/[^\d,]/g, "").split(",");
    return { r: rgb[0], g: rgb[1], b: rgb[2], a: "1" };
  }
  if (typeof __color === "object" && __color !== null) {
    return "rgb(" + __color.r + "," + __color.g + "," + __color.b + ")";
  }
  return null;
}

/************* STATES from/to localStorage *************/
/**
 * Get my player data from localStorage or create default
 */
export function getMyPlayerData() {
  return (
    JSON.parse(localStorage.getItem("myPlayerData")) || {
      username: getRandomName(),
      clientId: null,
      color: "rgb(128,128,128)",
      ready: true,
      firstname: null,
      name: null,
      email: null,
      ranking: 0,
    }
  );
}

/**
 * Get gameId from localStorage or set null
 */
export function getGameId() {
  return JSON.parse(localStorage.getItem("gameId")) || null;
}

/**
 * Get canvas config from localStorage or set default
 */
export function getCanvasConfig() {
  return (
    JSON.parse(localStorage.getItem("canvasConfig")) || {
      height: 400,
      width: 400,
      lineThickness: 5,
    }
  );
}

/**
 * Get public games from localStorage or set an empty array
 */
export function getPublicGames() {
  return JSON.parse(localStorage.getItem("publicGames")) || [];
}

/**
 * Get game config from localStorage or set empty
 */
export function getGameConfig() {
  return JSON.parse(localStorage.getItem("gameConfig")) || null;
}

/************* Random Name *************/
/**
 * Creates a random name object
 */
export function getRandomName() {
  var randomName = {
    firstName: "",
    lastNamePrefix: "",
    lastNameSuffix: "",
  };
  Object.keys(randomName).forEach((name) => {
    randomName[name] = getRandom(nameData[name]);
  });
  return (
    randomName.firstName +
    " " +
    randomName.lastNamePrefix +
    randomName.lastNameSuffix
  );
}

/**
 * Get random name
 * @param {*} items
 */
function getRandom(items) {
  return items[Math.floor(Math.random() * items.length)];
}

/**
 * Random name data
 */
const nameData = {
  firstName: [
    "nar",
    "An",
    "Alfr",
    "Alvi",
    "Ari",
    "Arinbjorn",
    "Arngeir",
    "Arngrim",
    "Arnfinn",
    "Asgeirr",
    "Askell",
    "Asvald",
    "Bard",
    "Baror",
    "Bersi",
    "Borkr",
    "Bjarni",
    "Bjorn",
    "Brand",
    "Brandr",
    "Cairn",
    "Canute",
    "Christian",
    "Dar",
    "Deniz",
    "Einarr",
    "Eirik",
    "Egill",
    "Engli",
    "Eyvindr",
    "Erik",
    "Eyvind",
    "Finnr",
    "Floki",
    "Fromund",
    "Geirmundr",
    "Geirr",
    "Geri",
    "Gisli",
    "Gizzur",
    "Gjafvaldr",
    "Glumr",
    "Gorm",
    "Grmir",
    "Gunnarr",
    "Guomundr",
    "Hak",
    "Halbjorn",
    "Halfdan",
    "Hallvard",
    "Hamal",
    "Hamundr",
    "Harald",
    "Harek",
    "Hedinn",
    "Helgi",
    "Henrik",
    "Herbjorn",
    "Herjolfr",
    "Hildir",
    "Hogni",
    "Hrani",
    "Ivan",
    "Ivarr",
    "Hrolf",
    "Jimmy",
    "Jon",
    "Johan",
    "Jorund",
    "Kalf",
    "Ketil",
    "Kheldar",
    "Klaengr",
    "Knut",
    "Kolbeinn",
    "Kolli",
    "Kollr",
    "Lambi",
    "Magnus",
    "Mike",
    "Moldof",
    "Mursi",
    "Njall",
    "Oddr",
    "Oger",
    "Olaf",
    "Orlyg",
    "Ormr",
    "Ornolf",
    "Osvald",
    "Ozurr",
    "Poror",
    "Prondir",
    "Patrick",
    "Ragi",
    "Ragnvald",
    "Refr",
    "Runolf",
    "Saemund",
    "Siegfried",
    "Sigmundr",
    "Sigurd",
    "Sigvat",
    "Skeggi",
    "Skomlr",
    "Slode",
    "Snorri",
    "Sokkolf",
    "Solvi",
    "Surt",
    "Sven",
    "Thangbrand",
    "Thjodoft",
    "Thorod",
    "Thorgest",
    "Thorvald",
    "Thrain",
    "Throst",
    "Torfi",
    "Torix",
    "Tryfing",
    "Ulf",
    "Valgaror",
    "Vali",
    "Vifil",
    "Vigfus",
    "Vika",
    "Waltheof",
  ],
  lastNamePrefix: [
    "Aesir",
    "Axe",
    "Battle",
    "Bear",
    "Berg",
    "Biscuit",
    "Black",
    "Blade",
    "Blood",
    "Blue",
    "Boar",
    "Board",
    "Bone",
    "Cage",
    "Cave",
    "Chain",
    "Cloud",
    "Coffee",
    "Code",
    "Death",
    "Dragon",
    "Dwarf",
    "Eel",
    "Egg",
    "Elk",
    "Fire",
    "Fjord",
    "Flame",
    "Flour",
    "Forge",
    "Fork",
    "Fox",
    "Frost",
    "Furnace",
    "Cheese",
    "Giant",
    "Glacier",
    "Goat",
    "God",
    "Gold",
    "Granite",
    "Griffon",
    "Grim",
    "Haggis",
    "Hall",
    "Hamarr",
    "Helm",
    "Horn",
    "Horse",
    "House",
    "Huskarl",
    "Ice",
    "Iceberg",
    "Icicle",
    "Iron",
    "Jarl",
    "Kelp",
    "Kettle",
    "Kraken",
    "Lake",
    "Light",
    "Long",
    "Mace",
    "Mead",
    "Maelstrom",
    "Mail",
    "Mammoth",
    "Man",
    "Many",
    "Mountain",
    "Mutton",
    "Noun",
    "Oath",
    "One",
    "Owl",
    "Pain",
    "Peak",
    "Pine",
    "Pot",
    "Rabbit",
    "Rat",
    "Raven",
    "Red",
    "Refreshingbeverage",
    "Ring",
    "Rime",
    "Rock",
    "Root",
    "Rune",
    "Salmon",
    "Sap",
    "Sea",
    "Seven",
    "Shield",
    "Ship",
    "Silver",
    "Sky",
    "Slush",
    "Smoke",
    "Snow",
    "Spear",
    "Squid",
    "Steam",
    "Stone",
    "Storm",
    "Swine",
    "Sword",
    "Three",
    "Tongue",
    "Torch",
    "Troll",
    "Two",
    "Ulfsark",
    "Umlaut",
    "Unsightly",
    "Valkyrie",
    "Wave",
    "White",
    "Wolf",
    "Woman",
    "Worm",
    "Wyvern",
  ],
  lastNameSuffix: [
    "admirer",
    "arm",
    "axe",
    "back",
    "bane",
    "baker",
    "basher",
    "beard",
    "bearer",
    "bender",
    "blade",
    "bleeder",
    "blender",
    "blood",
    "boiler",
    "bone",
    "boot",
    "borer",
    "born",
    "bow",
    "breaker",
    "breeder",
    "bringer",
    "brow",
    "builder",
    "chaser",
    "chiller",
    "collar",
    "counter",
    "curser",
    "crusher",
    "dancer",
    "deck",
    "dottir",
    "doubter",
    "dreamer",
    "drinker",
    "drowner",
    "ear",
    "eater",
    "face",
    "fearer",
    "friend",
    "foot",
    "fury",
    "gorer",
    "grim",
    "grinder",
    "grower",
    "growth",
    "hacker",
    "hall",
    "hammer",
    "hand",
    "hands",
    "head",
    "hilt",
    "hugger",
    "hunter",
    "howler",
    "killer",
    "leg",
    "licker",
    "liker",
    "lost",
    "lover",
    "maker",
    "mender",
    "minder",
    "miner",
    "mocker",
    "monger",
    "neck",
    "puncher",
    "rage",
    "rhyme",
    "rider",
    "ringer",
    "roarer",
    "roller",
    "sailor",
    "screamer",
    "sequel",
    "server",
    "shield",
    "shoe",
    "shoter",
    "singer",
    "skinner",
    "slinger",
    "slugger",
    "sniffer",
    "son",
    "smasher",
    "speaker",
    "stinker",
    "sucker",
    "sword",
    "tail",
    "tamer",
    "taster",
    "thigh",
    "tongue",
    "tosser",
    "tracker",
    "washer",
    "wielder",
    "wing",
    "wisher",
    "wrath",
  ],
};
