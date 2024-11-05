package top.lingyuzhao.diskMirrorTcpServer;

import org.slf4j.Logger;
import top.lingyuzhao.diskMirror.backEnd.conf.DiskMirrorConfig;
import top.lingyuzhao.diskMirror.backEnd.conf.WebConf;
import top.lingyuzhao.diskMirror.conf.Config;
import top.lingyuzhao.diskMirror.core.DiskMirror;
import top.lingyuzhao.diskMirror.core.TcpAdapter;
import top.lingyuzhao.diskMirrorTcpServer.runnable.TcpHandleRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    // 创建日志
    public static final Logger logger = WebConf.LOGGER;

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: <ThreadNumber> <tcpMetaPort,fileMetaPort>");
            return;
        }

        // 获取到线程数量
        final int threadNumber = Integer.parseInt(args[0]);


        logger.info("threadNumber = " + threadNumber);

        // 创建一个固定大小的线程池
        final ExecutorService executor = Executors.newFixedThreadPool(threadNumber); // 根据需要调整线程数量
        // 使用适配器对象 用来接收远程数据并进行处理
        final DiskMirror diskMirror = (DiskMirror) DiskMirrorConfig.getOption(WebConf.IO_MODE);
        final TcpAdapter adapterPacking0 = (TcpAdapter) DiskMirror.TCP_Adapter.getAdapterPacking(
                diskMirror,
                new ConfigTCPAdapter1(args[1]),
                new Config(DiskMirrorConfig.WEB_CONF)
        );

        logger.info("diskMirror tcp server is ok!\n" + diskMirror.getVersion());
        logger.info(adapterPacking0.version());
        logger.info("sk = " + DiskMirrorConfig.WEB_CONF.get(Config.SECURE_KEY));

        // 提交任务给线程池
        for (int i = 0; i < threadNumber; i++) { // 根据需要调整提交的任务数量
            executor.submit(new TcpHandleRunnable(adapterPacking0, "Thread-" + i));
        }

        // 如果你需要优雅地关闭线程池，可以在某个条件满足后调用 shutdown
        // executor.shutdown();
        // 或者等待所有已提交的任务完成后再关闭
        // executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public static final class ConfigTCPAdapter1 extends Config {
        public ConfigTCPAdapter1(String FS_DEFAULT_FS) {
            super.put(Config.FS_DEFAULT_FS, FS_DEFAULT_FS);
        }
    }
}