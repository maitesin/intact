select count(1), cv.shortlabel
from ia_component c, ia_controlledvocab cv
where c.biologicalrole_ac = cv.ac
group by cv.shortlabel;