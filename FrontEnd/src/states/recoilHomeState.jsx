import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

const bottomBarAtom = atom({
  key: "bottomBarAtom",
  default: 0,
  effects_UNSTABLE: [persistAtom],
});

const babyPK = atom({
  key: "babyPK",
  default: [],
  effects_UNSTABLE: [persistAtom],
});

const jwtToken = atom({
  key: "jwtToken",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

const deviceDataAtom = atom({
  key: "deviceDataAtom",
  default: [],
  effects_UNSTABLE: [persistAtom],
});

const serialNumberAtom = atom({
  key: "serialNumberAtom",
  default: "",
  effects_UNSTABLE: [persistAtom],
});

export { bottomBarAtom, babyPK, jwtToken, deviceDataAtom, serialNumberAtom };
