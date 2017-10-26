# Jupyter notebooks
This note presents some of the notebook we have developed to run on Data Science Experiences on IBM Cloud Private.

The Jupyter Notebook is an open-source web application that allows you to create and share documents that contain live code, equations, visualizations and narrative text. http://jupyter.org

As a quick summary: The notebook front end runs in web browser, the notebook server is responsible to delegate execution to kernels and persist notebook as json file on the file system where the server is started.
For python the kernel is ipython. Here is a list of [available kernels](https://github.com/jupyter/jupyter/wiki/Jupyter-kernels)

## Notebooks
* [Telco churn analysis](./TelcoChurn/README.md)

## Best practices on using jupyter notebooks
Take into consideration where to start developing a notebook: locally or in DSX. You can start locally to draft your work and migrate to DSX as soon as you need to collaborate with other persons. When DSX local is deployed to a central cluster as IBM Cloud Private, you should start from DSX.

### Common notebook development best practices
* do all imports in first code cell
Do all your imports in the first cell of your notebook.
* Start as early as possible, just out of a design thinking session. Keep the different draft version of your notebook in code repository
* Wrap cell content in function
* Use joblib for caching output of any function
```
from sklearn.externals.joblib import Memory
memory = Memory(cachedir='/tmp', verbose=0)
@memory.cache
def afunction(p1, p2):
```
* Make cells loosely bound and avoid global variables.
* Use assertions to test code and validate results

### Local best practices
* use virtual environment, and install jupyter notebook in those env.
```
virtualenv -p /Library/Frameworks/Python.framework/Versions/3.4/bin/python3.4 .venv
# Activate environment
$ source .venv/bin/activate
$ python3 -m pip install jupyter
# Verify you are using the local one
$ which jupyter
```
* Create dependencies requirements once your notebook is valid.
```
pip freeze > requirememts.txt
```
