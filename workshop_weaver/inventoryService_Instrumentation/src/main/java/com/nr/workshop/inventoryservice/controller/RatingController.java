package com.nr.workshop.inventoryservice.controller;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Weave(type = MatchType.ExactClass)
public class RatingController {
    @Trace
    public ResponseEntity<Integer> checkRating(@PathVariable String vendor )  {
        NewRelic.addCustomParameter("vendor",vendor);
        NewRelic.incrementCounter("Custom/CheckingRating/"+vendor);
        return Weaver.callOriginal();
    }
}
