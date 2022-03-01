package com.kaos.his.controller.impl.thread;

import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.inf.thread.PoolController;
import com.kaos.his.util.helper.ThreadPool;
import com.kaos.his.util.helper.ThreadPool.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms/thread/pool")
public class PoolControllerImpl implements PoolController {
    @Autowired
    ThreadPool threadPool;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = MediaType.JSON)
    public View show() {
        return this.threadPool.show();
    }
}
