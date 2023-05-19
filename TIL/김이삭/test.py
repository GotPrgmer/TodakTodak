import pygaze
from pygaze.display import Display
from pygaze.screen import Screen
from pygaze.eyetracker import EyeTracker
from pygaze import libtime
import time

# 초기 설정
DISPTYPE = 'psychopy'
TRACKERTYPE = 'eyetribe'

# 디스플레이, 스크린, 아이트래커 객체 생성
disp = Display()
scr = Screen()
tracker = EyeTracker(disp, TRACKERTYPE)

# 트래커 시작
tracker.start_recording()

# 고정점 그리기
scr.draw_fixation(fixtype='dot', pos=(disp.width/2, disp.height/2), colour='black')
disp.fill(scr)
disp.show()

# 고정점 주시 시작 시간 기록
t0 = libtime.get_time()
tracker.log('FIXATION_START')

# 10초 동안 고정점을 주시
while libtime.get_time() - t0 < 10000:
    # 트래커에서 최신 데이터 가져오기
    gaze_pos = tracker.sample()

    # 필요한 경우, 시선 위치를 기록하거나 사용
    print("Gaze position:", gaze_pos)
    time.sleep(0.1)

# 고정점 주시 종료 시간 기록
tracker.log('FIXATION_END')

# 트래커 종료
tracker.stop_recording()
tracker.close()

# 디스플레이 종료
disp.close()
