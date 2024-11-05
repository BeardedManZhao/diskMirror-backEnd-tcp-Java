package top.lingyuzhao.diskMirrorTcpServer.runnable;

import org.slf4j.Logger;
import top.lingyuzhao.diskMirror.backEnd.conf.WebConf;

/**
 * 处理器任务类的抽象类 此类可以接收一个 AdapterPacking 适配器 并启动此适配器的服务
 *
 * @param <T> 此适配器中的 AdapterPacking 适配器的具体类型
 * @author zhao
 */
public abstract class HandleRunnable<T> implements Runnable {

    public static final Logger logger = WebConf.LOGGER;
    private final T adapterPacking;
    private final String runnableName;


    public HandleRunnable(T adapterPacking, String runnableName) {
        this.adapterPacking = adapterPacking;
        this.runnableName = runnableName;
    }

    public T getAdapterPacking() {
        return adapterPacking;
    }

    public String getRunnableName() {
        return runnableName;
    }
}
