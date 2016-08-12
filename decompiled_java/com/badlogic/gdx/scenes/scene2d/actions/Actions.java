// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class Actions {
    public Actions() {
        super();
    }

    public static Action action(Class arg2) {
        Pool v1 = Pools.get(arg2);
        Object v0 = v1.obtain();
        ((Action)v0).setPool(v1);
        return ((Action)v0);
    }

    public static AddAction addAction(Action action) {
        Action v0 = Actions.action(AddAction.class);
        ((AddAction)v0).setAction(action);
        return ((AddAction)v0);
    }

    public static AddAction addAction(Action action, Actor targetActor) {
        Action v0 = Actions.action(AddAction.class);
        ((AddAction)v0).setTarget(targetActor);
        ((AddAction)v0).setAction(action);
        return ((AddAction)v0);
    }

    public static AddListenerAction addListener(EventListener listener, boolean capture) {
        Action v0 = Actions.action(AddListenerAction.class);
        ((AddListenerAction)v0).setListener(listener);
        ((AddListenerAction)v0).setCapture(capture);
        return ((AddListenerAction)v0);
    }

    public static AddListenerAction addListener(EventListener listener, boolean capture, Actor targetActor) {
        Action v0 = Actions.action(AddListenerAction.class);
        ((AddListenerAction)v0).setTarget(targetActor);
        ((AddListenerAction)v0).setListener(listener);
        ((AddListenerAction)v0).setCapture(capture);
        return ((AddListenerAction)v0);
    }

    public static AfterAction after(Action action) {
        Action v0 = Actions.action(AfterAction.class);
        ((AfterAction)v0).setAction(action);
        return ((AfterAction)v0);
    }

    public static AlphaAction alpha(float a) {
        return Actions.alpha(a, 0f, null);
    }

    public static AlphaAction alpha(float a, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(AlphaAction.class);
        ((AlphaAction)v0).setAlpha(a);
        ((AlphaAction)v0).setDuration(duration);
        ((AlphaAction)v0).setInterpolation(interpolation);
        return ((AlphaAction)v0);
    }

    public static AlphaAction alpha(float a, float duration) {
        return Actions.alpha(a, duration, null);
    }

    public static ColorAction color(Color color) {
        return Actions.color(color, 0f, null);
    }

    public static ColorAction color(Color color, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(ColorAction.class);
        ((ColorAction)v0).setEndColor(color);
        ((ColorAction)v0).setDuration(duration);
        ((ColorAction)v0).setInterpolation(interpolation);
        return ((ColorAction)v0);
    }

    public static ColorAction color(Color color, float duration) {
        return Actions.color(color, duration, null);
    }

    public static DelayAction delay(float duration) {
        Action v0 = Actions.action(DelayAction.class);
        ((DelayAction)v0).setDuration(duration);
        return ((DelayAction)v0);
    }

    public static DelayAction delay(float duration, Action delayedAction) {
        Action v0 = Actions.action(DelayAction.class);
        ((DelayAction)v0).setDuration(duration);
        ((DelayAction)v0).setAction(delayedAction);
        return ((DelayAction)v0);
    }

    public static AlphaAction fadeIn(float duration) {
        return Actions.alpha(1f, duration, null);
    }

    public static AlphaAction fadeIn(float duration, Interpolation interpolation) {
        Action v0 = Actions.action(AlphaAction.class);
        ((AlphaAction)v0).setAlpha(1f);
        ((AlphaAction)v0).setDuration(duration);
        ((AlphaAction)v0).setInterpolation(interpolation);
        return ((AlphaAction)v0);
    }

    public static AlphaAction fadeOut(float duration) {
        return Actions.alpha(0f, duration, null);
    }

    public static AlphaAction fadeOut(float duration, Interpolation interpolation) {
        Action v0 = Actions.action(AlphaAction.class);
        ((AlphaAction)v0).setAlpha(0f);
        ((AlphaAction)v0).setDuration(duration);
        ((AlphaAction)v0).setInterpolation(interpolation);
        return ((AlphaAction)v0);
    }

    public static RepeatAction forever(Action repeatedAction) {
        Action v0 = Actions.action(RepeatAction.class);
        ((RepeatAction)v0).setCount(-1);
        ((RepeatAction)v0).setAction(repeatedAction);
        return ((RepeatAction)v0);
    }

    public static VisibleAction hide() {
        return Actions.visible(false);
    }

    public static LayoutAction layout(boolean enabled) {
        Action v0 = Actions.action(LayoutAction.class);
        ((LayoutAction)v0).setLayoutEnabled(enabled);
        return ((LayoutAction)v0);
    }

    public static MoveByAction moveBy(float amountX, float amountY) {
        return Actions.moveBy(amountX, amountY, 0f, null);
    }

    public static MoveByAction moveBy(float amountX, float amountY, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(MoveByAction.class);
        ((MoveByAction)v0).setAmount(amountX, amountY);
        ((MoveByAction)v0).setDuration(duration);
        ((MoveByAction)v0).setInterpolation(interpolation);
        return ((MoveByAction)v0);
    }

    public static MoveByAction moveBy(float amountX, float amountY, float duration) {
        return Actions.moveBy(amountX, amountY, duration, null);
    }

    public static MoveToAction moveTo(float x, float y) {
        return Actions.moveTo(x, y, 0f, null);
    }

    public static MoveToAction moveTo(float x, float y, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(MoveToAction.class);
        ((MoveToAction)v0).setPosition(x, y);
        ((MoveToAction)v0).setDuration(duration);
        ((MoveToAction)v0).setInterpolation(interpolation);
        return ((MoveToAction)v0);
    }

    public static MoveToAction moveTo(float x, float y, float duration) {
        return Actions.moveTo(x, y, duration, null);
    }

    public static MoveToAction moveToAligned(float x, float y, int alignment) {
        return Actions.moveToAligned(x, y, alignment, 0f, null);
    }

    public static MoveToAction moveToAligned(float x, float y, int alignment, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(MoveToAction.class);
        ((MoveToAction)v0).setPosition(x, y, alignment);
        ((MoveToAction)v0).setDuration(duration);
        ((MoveToAction)v0).setInterpolation(interpolation);
        return ((MoveToAction)v0);
    }

    public static MoveToAction moveToAligned(float x, float y, int alignment, float duration) {
        return Actions.moveToAligned(x, y, alignment, duration, null);
    }

    public static ParallelAction parallel() {
        return Actions.action(ParallelAction.class);
    }

    public static ParallelAction parallel(Action action1) {
        Action v0 = Actions.action(ParallelAction.class);
        ((ParallelAction)v0).addAction(action1);
        return ((ParallelAction)v0);
    }

    public static ParallelAction parallel(Action action1, Action action2) {
        Action v0 = Actions.action(ParallelAction.class);
        ((ParallelAction)v0).addAction(action1);
        ((ParallelAction)v0).addAction(action2);
        return ((ParallelAction)v0);
    }

    public static ParallelAction parallel(Action action1, Action action2, Action action3) {
        Action v0 = Actions.action(ParallelAction.class);
        ((ParallelAction)v0).addAction(action1);
        ((ParallelAction)v0).addAction(action2);
        ((ParallelAction)v0).addAction(action3);
        return ((ParallelAction)v0);
    }

    public static ParallelAction parallel(Action action1, Action action2, Action action3, Action action4) {
        Action v0 = Actions.action(ParallelAction.class);
        ((ParallelAction)v0).addAction(action1);
        ((ParallelAction)v0).addAction(action2);
        ((ParallelAction)v0).addAction(action3);
        ((ParallelAction)v0).addAction(action4);
        return ((ParallelAction)v0);
    }

    public static ParallelAction parallel(Action action1, Action action2, Action action3, Action action4, Action action5) {
        Action v0 = Actions.action(ParallelAction.class);
        ((ParallelAction)v0).addAction(action1);
        ((ParallelAction)v0).addAction(action2);
        ((ParallelAction)v0).addAction(action3);
        ((ParallelAction)v0).addAction(action4);
        ((ParallelAction)v0).addAction(action5);
        return ((ParallelAction)v0);
    }

    public static ParallelAction parallel(Action[] actions) {
        Action v0 = Actions.action(ParallelAction.class);
        int v1 = 0;
        int v2 = actions.length;
        while(v1 < v2) {
            ((ParallelAction)v0).addAction(actions[v1]);
            ++v1;
        }

        return ((ParallelAction)v0);
    }

    public static RemoveAction removeAction(Action action) {
        Action v0 = Actions.action(RemoveAction.class);
        ((RemoveAction)v0).setAction(action);
        return ((RemoveAction)v0);
    }

    public static RemoveAction removeAction(Action action, Actor targetActor) {
        Action v0 = Actions.action(RemoveAction.class);
        ((RemoveAction)v0).setTarget(targetActor);
        ((RemoveAction)v0).setAction(action);
        return ((RemoveAction)v0);
    }

    public static RemoveActorAction removeActor() {
        return Actions.action(RemoveActorAction.class);
    }

    public static RemoveActorAction removeActor(Actor removeActor) {
        Action v0 = Actions.action(RemoveActorAction.class);
        ((RemoveActorAction)v0).setTarget(removeActor);
        return ((RemoveActorAction)v0);
    }

    public static RemoveListenerAction removeListener(EventListener listener, boolean capture) {
        Action v0 = Actions.action(RemoveListenerAction.class);
        ((RemoveListenerAction)v0).setListener(listener);
        ((RemoveListenerAction)v0).setCapture(capture);
        return ((RemoveListenerAction)v0);
    }

    public static RemoveListenerAction removeListener(EventListener listener, boolean capture, Actor targetActor) {
        Action v0 = Actions.action(RemoveListenerAction.class);
        ((RemoveListenerAction)v0).setTarget(targetActor);
        ((RemoveListenerAction)v0).setListener(listener);
        ((RemoveListenerAction)v0).setCapture(capture);
        return ((RemoveListenerAction)v0);
    }

    public static RepeatAction repeat(int count, Action repeatedAction) {
        Action v0 = Actions.action(RepeatAction.class);
        ((RepeatAction)v0).setCount(count);
        ((RepeatAction)v0).setAction(repeatedAction);
        return ((RepeatAction)v0);
    }

    public static RotateByAction rotateBy(float rotationAmount) {
        return Actions.rotateBy(rotationAmount, 0f, null);
    }

    public static RotateByAction rotateBy(float rotationAmount, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(RotateByAction.class);
        ((RotateByAction)v0).setAmount(rotationAmount);
        ((RotateByAction)v0).setDuration(duration);
        ((RotateByAction)v0).setInterpolation(interpolation);
        return ((RotateByAction)v0);
    }

    public static RotateByAction rotateBy(float rotationAmount, float duration) {
        return Actions.rotateBy(rotationAmount, duration, null);
    }

    public static RotateToAction rotateTo(float rotation) {
        return Actions.rotateTo(rotation, 0f, null);
    }

    public static RotateToAction rotateTo(float rotation, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(RotateToAction.class);
        ((RotateToAction)v0).setRotation(rotation);
        ((RotateToAction)v0).setDuration(duration);
        ((RotateToAction)v0).setInterpolation(interpolation);
        return ((RotateToAction)v0);
    }

    public static RotateToAction rotateTo(float rotation, float duration) {
        return Actions.rotateTo(rotation, duration, null);
    }

    public static RunnableAction run(Runnable runnable) {
        Action v0 = Actions.action(RunnableAction.class);
        ((RunnableAction)v0).setRunnable(runnable);
        return ((RunnableAction)v0);
    }

    public static ScaleByAction scaleBy(float amountX, float amountY) {
        return Actions.scaleBy(amountX, amountY, 0f, null);
    }

    public static ScaleByAction scaleBy(float amountX, float amountY, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(ScaleByAction.class);
        ((ScaleByAction)v0).setAmount(amountX, amountY);
        ((ScaleByAction)v0).setDuration(duration);
        ((ScaleByAction)v0).setInterpolation(interpolation);
        return ((ScaleByAction)v0);
    }

    public static ScaleByAction scaleBy(float amountX, float amountY, float duration) {
        return Actions.scaleBy(amountX, amountY, duration, null);
    }

    public static ScaleToAction scaleTo(float x, float y) {
        return Actions.scaleTo(x, y, 0f, null);
    }

    public static ScaleToAction scaleTo(float x, float y, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(ScaleToAction.class);
        ((ScaleToAction)v0).setScale(x, y);
        ((ScaleToAction)v0).setDuration(duration);
        ((ScaleToAction)v0).setInterpolation(interpolation);
        return ((ScaleToAction)v0);
    }

    public static ScaleToAction scaleTo(float x, float y, float duration) {
        return Actions.scaleTo(x, y, duration, null);
    }

    public static SequenceAction sequence() {
        return Actions.action(SequenceAction.class);
    }

    public static SequenceAction sequence(Action action1) {
        Action v0 = Actions.action(SequenceAction.class);
        ((SequenceAction)v0).addAction(action1);
        return ((SequenceAction)v0);
    }

    public static SequenceAction sequence(Action action1, Action action2) {
        Action v0 = Actions.action(SequenceAction.class);
        ((SequenceAction)v0).addAction(action1);
        ((SequenceAction)v0).addAction(action2);
        return ((SequenceAction)v0);
    }

    public static SequenceAction sequence(Action action1, Action action2, Action action3) {
        Action v0 = Actions.action(SequenceAction.class);
        ((SequenceAction)v0).addAction(action1);
        ((SequenceAction)v0).addAction(action2);
        ((SequenceAction)v0).addAction(action3);
        return ((SequenceAction)v0);
    }

    public static SequenceAction sequence(Action action1, Action action2, Action action3, Action action4) {
        Action v0 = Actions.action(SequenceAction.class);
        ((SequenceAction)v0).addAction(action1);
        ((SequenceAction)v0).addAction(action2);
        ((SequenceAction)v0).addAction(action3);
        ((SequenceAction)v0).addAction(action4);
        return ((SequenceAction)v0);
    }

    public static SequenceAction sequence(Action action1, Action action2, Action action3, Action action4, Action action5) {
        Action v0 = Actions.action(SequenceAction.class);
        ((SequenceAction)v0).addAction(action1);
        ((SequenceAction)v0).addAction(action2);
        ((SequenceAction)v0).addAction(action3);
        ((SequenceAction)v0).addAction(action4);
        ((SequenceAction)v0).addAction(action5);
        return ((SequenceAction)v0);
    }

    public static SequenceAction sequence(Action[] actions) {
        Action v0 = Actions.action(SequenceAction.class);
        int v1 = 0;
        int v2 = actions.length;
        while(v1 < v2) {
            ((SequenceAction)v0).addAction(actions[v1]);
            ++v1;
        }

        return ((SequenceAction)v0);
    }

    public static VisibleAction show() {
        return Actions.visible(true);
    }

    public static SizeByAction sizeBy(float amountX, float amountY) {
        return Actions.sizeBy(amountX, amountY, 0f, null);
    }

    public static SizeByAction sizeBy(float amountX, float amountY, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(SizeByAction.class);
        ((SizeByAction)v0).setAmount(amountX, amountY);
        ((SizeByAction)v0).setDuration(duration);
        ((SizeByAction)v0).setInterpolation(interpolation);
        return ((SizeByAction)v0);
    }

    public static SizeByAction sizeBy(float amountX, float amountY, float duration) {
        return Actions.sizeBy(amountX, amountY, duration, null);
    }

    public static SizeToAction sizeTo(float x, float y) {
        return Actions.sizeTo(x, y, 0f, null);
    }

    public static SizeToAction sizeTo(float x, float y, float duration, Interpolation interpolation) {
        Action v0 = Actions.action(SizeToAction.class);
        ((SizeToAction)v0).setSize(x, y);
        ((SizeToAction)v0).setDuration(duration);
        ((SizeToAction)v0).setInterpolation(interpolation);
        return ((SizeToAction)v0);
    }

    public static SizeToAction sizeTo(float x, float y, float duration) {
        return Actions.sizeTo(x, y, duration, null);
    }

    public static TimeScaleAction timeScale(float scale, Action scaledAction) {
        Action v0 = Actions.action(TimeScaleAction.class);
        ((TimeScaleAction)v0).setScale(scale);
        ((TimeScaleAction)v0).setAction(scaledAction);
        return ((TimeScaleAction)v0);
    }

    public static TouchableAction touchable(Touchable touchable) {
        Action v0 = Actions.action(TouchableAction.class);
        ((TouchableAction)v0).setTouchable(touchable);
        return ((TouchableAction)v0);
    }

    public static VisibleAction visible(boolean visible) {
        Action v0 = Actions.action(VisibleAction.class);
        ((VisibleAction)v0).setVisible(visible);
        return ((VisibleAction)v0);
    }
}

