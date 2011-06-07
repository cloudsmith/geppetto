# About the gh-pages branch
The gh-pages branch contains the Geppetto web pages built with GitHub Pages (i.e. jekyll and liquid).

In addition to reading up on jekyll and liquid, there are a couple of things not easily understood
from the documentation.

When categories are automatically assigned to posts (e.g. ./faq/demos/_posts), any setting of 
category/categories inside the posts in that _posts directory has no effect. They only take effect
when specified for posts in ./_posts.

Note that the variable "page" is set to the page being rendered, and not "post" as the documentation alludes
to. The "page" variable simply contains different set of variables depending on if it is a post or a page.

