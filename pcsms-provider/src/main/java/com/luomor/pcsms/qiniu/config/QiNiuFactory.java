package com.luomor.pcsms.qiniu.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.luomor.pcsms.comm.constant.SupplierConstant;
import com.luomor.pcsms.provider.factory.AbstractProviderFactory;
import com.luomor.pcsms.qiniu.service.QiNiuSmsImpl;

/**
 * @author Peter
 * @Date: 2025/1/30 16:06 29
 * @描述: QiNiuFactory
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QiNiuFactory extends AbstractProviderFactory<QiNiuSmsImpl, QiNiuConfig> {

    private static final QiNiuFactory INSTANCE = new QiNiuFactory();


    public static QiNiuFactory instance() {
        return INSTANCE;
    }


    @Override
    public QiNiuSmsImpl createSms(QiNiuConfig qiNiuConfig) {
        return new QiNiuSmsImpl(qiNiuConfig);
    }

    @Override
    public String getSupplier() {
        return SupplierConstant.QINIU;
    }
}
