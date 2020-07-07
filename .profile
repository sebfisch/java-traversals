source /usr/share/git-core/git-prompt.sh
export PS1='\[\e[0;32m\]\u@\h \[\e[0;33m\]\W\[\e[0;34m\]$(__git_ps1 " %s")\[\e[m\] \$ '
