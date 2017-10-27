#!/bin/bash
docker run -v $(pwd)/jupyter-notebooks:/home/jovyan/work -it --rm -p 8888:8888 tensorflow-stack 
