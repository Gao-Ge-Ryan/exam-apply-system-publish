package top.gaogle.framework.aspectj;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import top.gaogle.framework.annotation.MoreTransaction;
import top.gaogle.framework.util.SpringUtil;

import java.util.Stack;

/**
 * 多数据源事务控制（多个数据库事务）
 */
@Aspect
@Component
public class TransactionAspect {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(top.gaogle.framework.annotation.MoreTransaction)")
    public void moreTransaction() {
    }


    @Around(value = "moreTransaction()&&@annotation(annotation)")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint, MoreTransaction annotation) throws Throwable {
        //存放事务管理器的栈
        Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack = new Stack<>();
        //存放事务的状态，每一个DataSourceTransactionManager 对应一个 TransactionStatus
        Stack<TransactionStatus> transactionStatusStack = new Stack<>();

        try {
            //判断自定义注解@MoreTransaction 是否传入事务管理器的名字，将自定义注解的值对应的事务管理器入栈
            if (!openTransaction(dataSourceTransactionManagerStack, transactionStatusStack, annotation)) {
                return null;
            }
            //执行业务方法
            Object ret = thisJoinPoint.proceed();
            //如果没有异常，说明两个sql都执行成功，两个数据源的sql全部提交事务
            commit(dataSourceTransactionManagerStack, transactionStatusStack);
            return ret;
        } catch (Throwable e) {
            //业务代码发生异常，回滚两个数据源的事务
            rollback(dataSourceTransactionManagerStack, transactionStatusStack);
            logger.error(String.format("MultiTransactionalAspect, method:%s-%s error:",
                    thisJoinPoint.getTarget().getClass().getSimpleName(), thisJoinPoint.getSignature().getName()), e);
            throw e;
        }
    }

    /**
     * 开启事务处理方法
     */
    private boolean openTransaction(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                                    Stack<TransactionStatus> transactionStatusStack, MoreTransaction multiTransactional) {
        // 获取需要开启事务的事务管理器名字
        String[] transactionMangerNames = multiTransactional.value();
        // 检查是否有需要开启事务的事务管理器名字
        if (ArrayUtils.isEmpty(multiTransactional.value())) {
            return false;
        }
        // 遍历事务管理器名字数组，逐个开启事务并将事务状态和管理器存入栈中
        for (String beanName : transactionMangerNames) {
            // 从Spring上下文中获取事务管理器
            DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) SpringUtil.getBean(beanName);
            // 创建新的事务状态
            TransactionStatus transactionStatus = dataSourceTransactionManager
                    .getTransaction(new DefaultTransactionDefinition());
            // 将事务状态和事务管理器存入对应的栈中
            transactionStatusStack.push(transactionStatus);
            dataSourceTransactionManagerStack.push(dataSourceTransactionManager);
        }
        return true;
    }

    /**
     * 提交处理方法
     */
    private void commit(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                        Stack<TransactionStatus> transactionStatusStack) {
        // 循环，直到事务管理器栈为空
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            // 从事务管理器栈和事务状态栈中分别弹出当前的事务管理器和事务状态
            // 提交当前事务状态
            dataSourceTransactionManagerStack.pop()
                    .commit(transactionStatusStack.pop());
        }
    }

    /**
     * 回滚处理方法
     */
    private void rollback(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                          Stack<TransactionStatus> transactionStatusStack) {
        // 循环，直到事务管理器栈为空
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            // 从事务管理器栈和事务状态栈中分别弹出当前的事务管理器和事务状态
            // 回滚当前事务状态
            dataSourceTransactionManagerStack.pop().rollback(transactionStatusStack.pop());
        }
    }
}

