package com.netty;

import java.util.Map;

/**
 * Request
 *
 * @author fishermen
 * @version V1.0 created at: 2013-5-16
 */
public interface Request {

    /**
     * service interface
     *
     * @return
     */
    String getInterfaceName();

    /**
     * service method name
     *
     * @return
     */
    String getMethodName();

    /**
     * service method param desc (sign)
     *
     * @return
     */
    String getParamtersDesc();

    /**
     * service method param
     *
     * @return
     */
    Object[] getArguments();

    /**
     * get framework param
     *
     * @return
     */
    Map<String, String> getAttachments();

    /**
     * set framework param
     *
     * @return
     */
    void setAttachment(String name, String value);

    /**
     * request id
     *
     * @return
     */
    long getRequestId();

    /**
     * retries
     *
     * @return
     */
    int getRetries();

    /**
     * set retries
     */
    void setRetries(int retries);

}
