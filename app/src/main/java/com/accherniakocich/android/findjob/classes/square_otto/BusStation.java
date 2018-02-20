package com.accherniakocich.android.findjob.classes.square_otto;

import com.squareup.otto.Bus;

public class BusStation{
    private static Bus bus = new Bus();

    public static Bus getBus() {
        return bus;
    }
}
