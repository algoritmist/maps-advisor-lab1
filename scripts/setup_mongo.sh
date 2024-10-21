#!/bin/bash
mongosh <<EOF
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
try {rs.status()} catch(err) {rs.initiate(config)}
rs.config(config, { force: true });
rs.status();
EOF
