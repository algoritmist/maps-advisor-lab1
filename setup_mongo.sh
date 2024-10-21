#!/bin/bash
mongo <<EOF
var config = {
    "_id": "rs0",
    "version": 1,
    "members": [
        {
            "_id": 0,
            "host": "mongo:27017",
            "priority": 3
        }
    ]
};
rs.reconfig(config, { force: true });
rs.status();
EOF
