import { atom } from "recoil";

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

export { modalStateAtom, alarmTitleAtom, alarmBodyAtom, alarmLinkAtom };
