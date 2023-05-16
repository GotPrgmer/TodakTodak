import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

const modalStateAtom = atom({
  key: "modalStateAtom",
  default: false,
});

const alarmDataAtom = atom({
  key: "alarmDataAtom",
  default: [],
  effects_UNSTABLE: [persistAtom],
});

const isReadAlarmAtom = atom({
  key: "isReadAlarmAtom",
  default: true,
  effects_UNSTABLE: [persistAtom],
});

const isReadEachAlarmAtom = atom({
  key: "isReadEachAlarmAtom",
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export { modalStateAtom, alarmDataAtom, isReadAlarmAtom, isReadEachAlarmAtom };
