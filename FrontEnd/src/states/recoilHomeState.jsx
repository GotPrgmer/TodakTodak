import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

const bottomBarAtom = atom({
  key: "bottomBarAtom",
  default: 0,
  effects_UNSTABLE: [persistAtom],
});

export { bottomBarAtom };
