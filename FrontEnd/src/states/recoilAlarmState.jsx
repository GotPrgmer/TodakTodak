import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

const modalStateAtom = atom({
  key: "modalStateAtom",
  default: false,
});

const alarmTitleAtom = atom({
  key: "alarmTitleAtom",
  default: "",
});

const alarmBodyAtom = atom({
  key: "alarmBodyAtom",
  default: "",
});

const alarmLinkAtom = atom({
  key: "alarmLinkAtom",
  default: "",
});

const alarmDataAtom = atom({
  key: "alarmDataAtom",
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export {
  modalStateAtom,
  alarmTitleAtom,
  alarmBodyAtom,
  alarmLinkAtom,
  alarmDataAtom,
};
