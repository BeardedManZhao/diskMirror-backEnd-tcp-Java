package top.lingyuzhao.diskMirrorTcpServer.runnable;

import top.lingyuzhao.diskMirror.core.TcpAdapter;

/**
 * @author zhao
 */
public class TcpHandleRunnable extends HandleRunnable<TcpAdapter> {

    public TcpHandleRunnable(TcpAdapter adapterPacking, String runnableName) {
        super(adapterPacking, runnableName);
        logger.info("TCP Handle Runnable " + runnableName + " init ok.");
    }

    @Override
    public void run() {
        try {
            final TcpAdapter adapterPacking = this.getAdapterPacking();
            logger.info("TCP Handle Runnable " + this.getRunnableName() + " started.");
            while (true) {
                adapterPacking.run();
            }
        } catch (Exception e) {
            logger.warn("请求处理错误", e);
        }
    }
}
