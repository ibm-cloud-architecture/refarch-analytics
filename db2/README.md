
# Deploy Db2 Warehouse to IBM Cloud Private

There are two deployment packages for Db2 on ICP, development and production. We used the production deployment.

A pre-requisite for deployment is to have a persistent volume that is at least 50 GiB.

## What Type of Persistent Storage to use

We looked at two common options, NFS and Gluster. A fairly simple but repeatable workload that had an IO component was used to test performance metrics. This workload was based on the TPC-D benchmark.

### NFS

This is the simplest storage to define. When Db2 was deployed using NFS as its persisten storage we saw the best performance with no additional tuning. For a quick, reloadable environment this is the simplest and fastest choice.

### Gluster

We used a 4-way dispersed volume with 2-brick redundancy for a total of 6 "disks". Initial deployment had performance characteristics 15-30% slower. Gluster does have the advatage of redundancy and parallelism of IO so would be recommended for longer-term database requirements in the environment.

#### Turbo-Charging Gluster

Since part of Gluster's advantage is dispersed IO and redundancy I made a simple change to how data was stored in DB2 that added another parallelization layer. Knowing that the gluster file system consisted of 6 bricks I created a new storage group to have 6 containers and tablespaces to use that storage group. I used `select db_storage_path from table(admin_get_storage_paths('',-1)) where storage_group_name = 'IBMSTOGROUP'` to find the path used by the deployment.

```
create stogroup sto6 on '/path', '/path', '/path', '/path', '/path', '/path';
create tablespace data1 automatic using stogroup sto6;
```

All tables would then be created in the data1 tablespace.

Alternatively you can run `alter stogroup ibmstogroup add '/path', '/path', '/path', '/path', '/path'` to modify the default storage group and all tables created afterward would then have the same IO characteristics.

Performance testing done with this new storage group were identical to NFS performance statistics.


