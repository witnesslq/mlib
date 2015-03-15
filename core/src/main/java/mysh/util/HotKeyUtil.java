
package mysh.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 热键工具类.
 *
 * @author Allen
 */
public class HotKeyUtil {
	/**
	 * 键-动作映射.
	 */
	private static final HashMap<KeyStroke, Queue<Action>> ActionMap = new HashMap<>();

	static {
		// 注册一个键盘事件发布器.
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher(e -> {
			KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
			// ActionMap doesn't need to be concurrent component because modify actions are protected
			// by monitor lock
			Queue<Action> actions = ActionMap.get(keyStroke);
			if (actions != null) {
				ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), null);
				for (Action action : actions) {
					SwingUtilities.invokeLater(() -> action.actionPerformed(ae));
				}
			}
			return false;
		});
	}

	/**
	 * 注册热键. <br/>
	 * 参数详见 {@link javax.swing.KeyStroke#getKeyStroke(int, int)}
	 *
	 * @param keyCode   键
	 * @param modifiers 功能键组合, 没有则为 0
	 * @param action    动作
	 */
	public static void registerHotKey(int keyCode, int modifiers, AbstractAction action) {
		synchronized (ActionMap) {
			KeyStroke ks = KeyStroke.getKeyStroke(keyCode, modifiers);
			Queue<Action> actions = ActionMap.get(ks);
			if (actions == null) {
				actions = new ConcurrentLinkedQueue<>();
				ActionMap.put(ks, actions);
			}
			actions.add(action);
		}
	}

	/**
	 * 反注册热键. <br/>
	 * 参数详见 {@link javax.swing.KeyStroke#getKeyStroke(int, int)}
	 *
	 * @param keyCode   键
	 * @param modifiers 功能键组合, 没有则为 0
	 * @param action    动作
	 */
	public static void unRegisterHotKey(int keyCode, int modifiers, AbstractAction action) {
		synchronized (ActionMap) {
			KeyStroke ks = KeyStroke.getKeyStroke(keyCode, modifiers);
			Queue<Action> actions = ActionMap.get(ks);
			if (actions != null) {
				actions.remove(action);
				if (actions.isEmpty())
					ActionMap.remove(ks);
			}
		}
	}
}
