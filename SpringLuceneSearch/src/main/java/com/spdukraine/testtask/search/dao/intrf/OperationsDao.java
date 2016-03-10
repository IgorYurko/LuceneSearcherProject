package com.spdukraine.testtask.search.dao.intrf;

import java.io.Serializable;

public interface OperationsDao<T extends Serializable>
{
    Boolean dump();

    Boolean cleanDumpDirectory();
}
