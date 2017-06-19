# plain2beamer

A simple tool converting plain text file (with syntax similar to Markdown) into LaTeX beamer presentation.


## Usage

Assume you have file `notes.txt` in specified format (see next section), to generate presentation, type


    $ java -jar target/plain2beamer-2.0.1.jar notes.txt presentation.tex && pdflatex presentation.tex  

If nothing fails you should get generated PDF `presentation.pdf` (with some more files, we all know LaTeX).

## Input file format
Input file format is as much as simple as it can be. It is similar to Markdown.

First, you should specify something about the presentation:

    title=My presentation
    author=me
    date=now
    version=1.0
    institute=My company


Then, you can continue with some other latex-specific headings, like:

    \usepackage{amsmath}
    
    \mode<presentation> {
      \usetheme{Pittsburgh}
      \usecolortheme{sea}
    }


When you type first `#` (new frame) or `##` (section) `###` (subsection) statement, plaint2beamer automatically starts the content of the presentation. Outputs the title slide, slide with contents and then your slides.

Slide can look like this (note usage of Tab instead of spaces in padding):

    #Slide header
    	- first bullet
    	- another one
    		- subitem
    	3. and the third one  

or 

    #
    This is headerless slide and with no bullets. Just simply a plain \LaTeX text inside of the slide.

And that's all folks. It's just simple. I know.

## TODO

 - translate to english (originally in chzech)
 - refactor, make it more data-oriented instead of stream-oriented
 - write tests