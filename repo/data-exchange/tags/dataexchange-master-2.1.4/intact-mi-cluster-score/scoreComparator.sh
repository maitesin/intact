MAVEN_OPTS="-Xms512m -Xmx2024m -XX:MaxPermSize=256m"

export MAVEN_OPTS


echo "MAVEN_OPTS=$MAVEN_OPTS"

echo $1
echo $2
echo $3
echo $4
echo $5
echo $6
echo $7
echo $8
echo $9
echo $10

mvn -U clean install -Pscore-comparator -DexportedA=$1 -DexportedB=$2 -DexcludedA=$3 -DexcludedB=$4 -Dresults=$5 -Dmaven.test.skip
